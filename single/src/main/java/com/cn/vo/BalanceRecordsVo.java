package com.cn.vo;

import ch.qos.logback.core.model.INamedModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.vo
 * @Author: 刘志威
 * @CreateTime: 2024-05-22  21:17
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
public class BalanceRecordsVo {

    private LocalDateTime createdTime;

    private String userPhoneNumber;

    private String Type;

    private String ModelName;

    private Long promptTokens;

    private Long completionTokens;

    private BigDecimal balance;


}
