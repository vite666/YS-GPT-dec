package com.cn.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.cn.common.DallCommon;
import com.cn.constant.DrawingConstant;
import com.cn.constant.DrawingStatusConstant;
import com.cn.dto.MjTaskDto;
import com.cn.dto.SdTaskDto;
import com.cn.entity.TsException;
import com.cn.entity.TsGenerateDrawing;
import com.cn.enums.DrawingTypeEnum;
import com.cn.enums.LevelEnum;
import com.cn.enums.ServerEnum;
import com.cn.mapper.TsExceptionMapper;
import com.cn.mapper.TsGenerateDrawingMapper;
import com.cn.model.DialogueGenerateModel;
import com.cn.model.MjModel;
import com.cn.model.SdModel;
import com.cn.service.MjService;
import com.cn.structure.TaskStructure;
import com.cn.utils.DrawingUtils;
import com.cn.utils.RedisUtils;
import com.cn.utils.StringUtils;
import com.cn.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.service.impl
 * @Author: 刘志威
 * @CreateTime: 2024-05-27  23:54
 * @Description: Mj 绘图服务层
 * @Version: 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MjServiceImpl implements MjService {
    /**
     * The Redis template.
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * The Redis utils.
     */
    private final RedisUtils redisUtils;

    /**
     * The Drawing utils.
     */
    private final DrawingUtils drawingUtils;

    /**
     * The Ts generate drawing mapper.
     */
    private final TsGenerateDrawingMapper tsGenerateDrawingMapper;

    private final TsExceptionMapper tsExceptionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addMjDrawingTask(final MjTaskDto dto) {

        UserUtils.membershipHasExpiredErrorMsg(StpUtil.getLoginIdAsLong());
        final Long currentLoginId = drawingUtils.verifyTask();
        final String key = DallCommon.pollGetKey();

        MjModel mjModel = new MjModel().setPrompt(dto.getPrompt());
        if (StringUtils.notEmpty(dto.getImages())) mjModel.setBase64Array(List.of(dto.getImages()));

        try {
            final String block = WebClient.builder().baseUrl(DallCommon.STRUCTURE.getRequestUrl())
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + key)
                    .codecs(item -> item.defaultCodecs().maxInMemorySize(20 * 1024 * 1024))
                    .build()
                    .post()
                    .uri("mj/submit/imagine")
                    .body(BodyInserters.fromValue(mjModel))
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), (clientResponse -> clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                final String errorCode = parseObject(errorBody).getString("error");
                                final JSONObject jsonObject = parseObject(errorCode);
                                connectionException(jsonObject.toString(), key);
                                return Mono.error(new RuntimeException());
                            })))
                    .bodyToMono(String.class).block();
            //解析JSON
            final JSONObject jsonObject = JSONObject.parseObject(block);
            final String taskId = jsonObject.getString("result");

            final String actualTaskId = DrawingConstant.TASK + taskId;

            //设置个人任务队列
            final TaskStructure taskStructure = new TaskStructure()
                    //设置任务状态为 构件中
                    .setTaskId(taskId)
                    .setStatus(DrawingStatusConstant.PENDING)
                    .setDrawingType(DrawingTypeEnum.MJ.getDec())
                    .setPrompt(dto.getPrompt());
            //设置个人任务
            redisUtils.setValueTimeout(actualTaskId, taskStructure, 1800);

            tsGenerateDrawingMapper.insert(new TsGenerateDrawing()
                    .setStatus(DrawingStatusConstant.PENDING)
                    .setPrompt(dto.getPrompt())
                    .setGenerateDrawingId(taskId)
                    .setType(DrawingTypeEnum.MJ.getDec())
                    .setUserId(currentLoginId)
            );
            redisTemplate.opsForList().leftPush(DrawingConstant.MJ_TASK_QUEUE, taskStructure.setExtra(mjModel));

            return taskId;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * 记录连接异常
     */
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
