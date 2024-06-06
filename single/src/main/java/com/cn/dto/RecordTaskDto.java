package com.cn.dto;

import com.cn.common.ChatGptCommon;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.dto
 * @Author: 刘志威
 * @CreateTime: 2024-06-04  10:45
 * @Description: 计费任务 DTO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class RecordTaskDto {
//    long userId, long promptTokens, long completionTokens, ChatGptCommon.ModelObj modelObj
    // 用户 ID
    Long userId;

    // 请求 Tokens
    Long promptTokens;

    // 补全 Tokens
    Long completionTokens;

    ChatGptCommon.ModelObj modelObj;
}
