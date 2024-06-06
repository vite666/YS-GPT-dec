package com.cn.listener;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.cn.common.DallCommon;
import com.cn.common.PoolCommon;
import com.cn.constant.DrawingConstant;
import com.cn.constant.DrawingStatusConstant;
import com.cn.entity.TsException;
import com.cn.entity.TsGenerateDrawing;
import com.cn.enums.DrawingTypeEnum;
import com.cn.enums.FileEnum;
import com.cn.enums.LevelEnum;
import com.cn.enums.ServerEnum;
import com.cn.exception.MjException;
import com.cn.mapper.TsExceptionMapper;
import com.cn.mapper.TsGenerateDrawingMapper;
import com.cn.model.DallModel;
import com.cn.model.MjModel;
import com.cn.structure.TaskStructure;
import com.cn.utils.RedisUtils;
import com.cn.utils.StringUtils;
import com.cn.utils.UploadUtil;
import com.cn.vo.TaskResultVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.listener
 * @Author: 刘志威
 * @CreateTime: 2024-05-28  15:50
 * @Description: MJ 绘图任务监听器
 * @Version: 1.0
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@SuppressWarnings("all")
@Configuration
public class MjTaskListener {
    private final RedisTemplate<String, Object> redisTemplate;

    private final WebClient.Builder webClient;

    private final ThreadPoolExecutor threadPoolExecutor;

    private final TsExceptionMapper tsExceptionMapper;

    private final UploadUtil uploadUtil;

    private final TsGenerateDrawingMapper tsGenerateDrawingMapper;
    private Semaphore semaphore;

    private final RedisUtils redisUtils;

    /**
     * 你会不会突然的出现
     */
    @EventListener(ApplicationReadyEvent.class)
    public void Mjlistening() {
        semaphore = new Semaphore(PoolCommon.STRUCTURE.getMjConcurrent());
        threadPoolExecutor.execute(() -> {
            while (true) {
                try {
                    semaphore.acquire();
                    //每三秒从队列中获取数据
                    final TaskStructure ts = (TaskStructure) redisTemplate.opsForList().rightPop(DrawingConstant.MJ_TASK_QUEUE, 3, TimeUnit.SECONDS);
                    try {
                        if (ts != null) {
                            this.handleMjGenerate(ts.getExtra(), ts.getTaskId());
                        }
                    } catch (MjException e) {
                        redisTemplate.opsForList().leftPush(DrawingConstant.MJ_TASK_QUEUE, ts);
                    } catch (Exception e) {
//                        设置报错信息
                        redisTemplate.opsForValue()
                                .set(DrawingConstant.TASK + ts.getTaskId(), ts
                                        .setStatus(DrawingStatusConstant.DISUSE), 600, TimeUnit.SECONDS);
                        //设置绘图失败标识
                        tsGenerateDrawingMapper.updateById(new TsGenerateDrawing()
                                .setStatus(DrawingStatusConstant.DISUSE)
                                .setGenerateDrawingId(ts.getTaskId()));
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release(); // 释放信号量许可
                }

            }
        });
    }

    /**
     * 处理 MJ 生成任务
     *
     * @param o      the o
     * @param taskId the task id
     */
    private void handleMjGenerate(final Object o, final String taskId) {
        final MjModel mjModel = (MjModel) o;
        //设置执行任务
        final TaskStructure taskStructure = new TaskStructure().setDrawingType(DrawingTypeEnum.MJ.getDec()).setTaskId(taskId).setPrompt(mjModel.getPrompt());
        final TsGenerateDrawing tsGenerateDrawing = new TsGenerateDrawing().setGenerateDrawingId(taskId);


        String url = "mj/task/" + taskId + "/fetch";
        final String key = DallCommon.pollGetKey();
        try {

            final String block = webClient.baseUrl(DallCommon.STRUCTURE.getRequestUrl())
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + key)
                    .codecs(item -> item.defaultCodecs().maxInMemorySize(20 * 1024 * 1024)).build()
                    .get()
                    .uri(url)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), (clientResponse -> clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                final String errorCode = parseObject(errorBody).getString("error");
                                final JSONObject jsonObject = parseObject(errorCode);
                                connectionException(jsonObject.toString(), key);
                                return Mono.error(new RuntimeException());
                            })))
                    .bodyToMono(String.class)
                    .doOnError(e -> {
                        // 记录错误日志
                        System.err.println("Error occurred: " + e.getMessage());
                        e.printStackTrace();
                    })
                    .block();

            final JSONObject jsonObject = JSONObject.parseObject(block);
            assert jsonObject != null;
            final String imageUrl = jsonObject.getString("imageUrl");
            final String progress = jsonObject.getString("progress");
            final String state = jsonObject.getString("status");
            if ("FAILURE".equals(state)) throw new Exception("任务失败");

            if (StringUtils.notEmpty(progress))
                redisTemplate.opsForHash().put(DrawingConstant.MJ_EXECUTION, taskId, progress);
            else
                redisTemplate.opsForHash().put(DrawingConstant.MJ_EXECUTION, taskId, "0%");


            if (!"100%".equals(progress)) throw new MjException("MJ 绘画未完成");

            //上传至阿里云
            final String drawingUrl = uploadUtil.uploadImageFromUrl(imageUrl, FileEnum.DRAWING.getDec());

            tsGenerateDrawing
                    .setStatus(DrawingStatusConstant.SUCCEED)
                    .setUrl(drawingUrl);
            //设置构建成功
            redisTemplate.opsForValue().set(DrawingConstant.TASK + taskId,
                    taskStructure
                            .setImageUrl(drawingUrl)
                            .setStatus(DrawingStatusConstant.SUCCEED)
                    , 600, TimeUnit.SECONDS);
            redisTemplate.opsForHash().delete(DrawingConstant.MJ_EXECUTION, taskId);

        } catch (MjException e) {
            throw e;
        } catch (Exception e) {
            connectionException(e.getMessage(), key);
            tsGenerateDrawing.setStatus(DrawingStatusConstant.DISUSE);
            //设置返回错误结果
            redisTemplate.opsForValue()
                    .set(DrawingConstant.TASK + taskId, taskStructure
                            .setStatus(DrawingStatusConstant.DISUSE), 600, TimeUnit.SECONDS);
            redisTemplate.opsForHash().delete(DrawingConstant.MJ_EXECUTION, taskId);
        } finally {

            tsGenerateDrawingMapper.updateById(tsGenerateDrawing);
        }
    }


    private void connectionException(String e, final String key) {
        final TsException dto = new TsException();
        //记录对话异常
        String str = "当前GPT密钥:" + key + "貌似有问题,在绘图时出现了:" + e + " 错误,请根据OPENAI提供的错误码进行相关操作";
        dto.setLevel(LevelEnum.MIDDLE.getDesc())
                .setServerName(ServerEnum.DRAWING.getDesc())
                .setCause(str);
        tsExceptionMapper.insert(dto);
    }
}
