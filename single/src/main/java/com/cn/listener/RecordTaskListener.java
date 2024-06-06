package com.cn.listener;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.cn.common.ChatGptCommon;
import com.cn.common.DallCommon;
import com.cn.common.PoolCommon;
import com.cn.constant.ChatGptConstant;
import com.cn.constant.DrawingConstant;
import com.cn.constant.DrawingStatusConstant;
import com.cn.constant.UserCacheConstant;
import com.cn.dto.RecordTaskDto;
import com.cn.entity.BalanceRecords;
import com.cn.entity.TsException;
import com.cn.entity.TsGenerateDrawing;
import com.cn.entity.TsUser;
import com.cn.enums.DrawingTypeEnum;
import com.cn.enums.FileEnum;
import com.cn.enums.LevelEnum;
import com.cn.enums.ServerEnum;
import com.cn.mapper.BalanceRecordMapper;
import com.cn.mapper.TsExceptionMapper;
import com.cn.mapper.TsGenerateDrawingMapper;
import com.cn.mapper.TsUserMapper;
import com.cn.model.DallModel;
import com.cn.structure.RecordTaskStructure;
import com.cn.structure.TaskStructure;
import com.cn.structure.UserInfoStructure;
import com.cn.utils.UploadUtil;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
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
 * @CreateTime: 2024-06-04  10:38
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@SuppressWarnings("all")
@Configuration
public class RecordTaskListener {

    private final RedisTemplate<String, Object> redisTemplate;

    private final WebClient.Builder webClient;

    private final ThreadPoolExecutor threadPoolExecutor;

    private final TsExceptionMapper tsExceptionMapper;

    private final BalanceRecordMapper balanceRecordMapper;

    private final TsUserMapper tsUserMapper;
    private Semaphore semaphore;



    @EventListener(ApplicationReadyEvent.class)
    public void recordListening() {
        semaphore = new Semaphore(PoolCommon.STRUCTURE.getRecordConcurrent());
        threadPoolExecutor.execute(() -> {
            while (true) {
                try {
                    semaphore.acquire();
                    //每 50 毫秒从队列中获取数据
                    final RecordTaskStructure ts = (RecordTaskStructure) redisTemplate.opsForList().rightPop(ChatGptConstant.RECORD_TASK_QUEUE, 50, TimeUnit.MILLISECONDS);
                    try {
                        if (ts != null) {
                            this.handleRecordGenerate(ts.getRecordTaskDto());
                        }
                    } catch (Exception e) {
                        // 放入任务队列重新计费
//                        redisTemplate.opsForList().leftPush(ChatGptConstant.RECORD_TASK_QUEUE, ts);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release(); // 释放信号量许可
                }


            }
        });
    }

    private void handleRecordGenerate(final RecordTaskDto dto) {
        try {

            long userId = dto.getUserId();
            long promptTokens = dto.getPromptTokens();
            long completionTokens = dto.getCompletionTokens();
            ChatGptCommon.ModelObj modelObj = dto.getModelObj();

            // 新增额度记录
            // 设置精度为9位小数，舍入模式为HALF_UP
            BigDecimal promptPrice = modelObj.getPromptPrice().divide(BigDecimal.valueOf(1000000), 9, RoundingMode.HALF_UP);
            BigDecimal completionPrice = modelObj.getCompletionPrice().divide(BigDecimal.valueOf(1000000), 9, RoundingMode.HALF_UP);

            BigDecimal promptCost = BigDecimal.valueOf(promptTokens).multiply(promptPrice);
            BigDecimal completionCost = BigDecimal.valueOf(completionTokens).multiply(completionPrice);
            BigDecimal totalCost = promptCost.add(completionCost); // 总消耗

            BalanceRecords balanceRecords = new BalanceRecords()
                    .setUserId(userId)
                    .setModelName(modelObj.getModelName())
                    .setPromptTokens(promptTokens)
                    .setCompletionTokens(completionTokens)
                    .setBalance(totalCost)
                    .setType("消费");

            balanceRecordMapper.insert(balanceRecords);

            // 更新用户数据
            TsUser tsUser = tsUserMapper.selectById(userId);
            tsUser.setAccountBalance(tsUser.getAccountBalance().subtract(totalCost));
            tsUserMapper.updateById(tsUser);

            // 更新用户缓存
            StpUtil.getSessionByLoginId(tsUser.getUserId())
                    .set(UserCacheConstant.USER_INFO_DATA, new UserInfoStructure()
                            .setUserID(tsUser.getUserId())
                            .setNickName(tsUser.getNickName())
                            .setExpirationTime(tsUser.getExpirationTime())
                            .setEmail(tsUser.getEmail())
                            .setType(tsUser.getType())
                            .setAvatar(tsUser.getAvatar())
                            .setAccountBlance(tsUser.getAccountBalance())
                            .setPhoneNumber(tsUser.getPhoneNumber())
                            .setOpenId(tsUser.getOpenId()));
        }catch (Exception e) {
            connectionException(e.getMessage());
            throw e;
        }

    }



    /**
     * 记录连接异常
     */
    private void connectionException(String e) {
        final TsException dto = new TsException();
        //记录对话异常
        String str = "貌似有问题,在计费时出现了:" + e + " 错误";
        dto.setLevel(LevelEnum.MIDDLE.getDesc())
                .setServerName(ServerEnum.DRAWING.getDesc())
                .setCause(str);
        tsExceptionMapper.insert(dto);
    }
}
