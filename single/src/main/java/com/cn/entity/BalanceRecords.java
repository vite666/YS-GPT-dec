package com.cn.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 余额记录表
 */
@Data
@Accessors(chain = true)
@TableName(value = "balance_records")
public class BalanceRecords {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long balanceRecordsId;

    /**
     * 时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;


    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 类型：消费或充值
     */
    private String type;

    /**
     * 使用模型
     */
    private String modelName;

    /**
     * 提示信息消耗 tokens
     */
    private Long promptTokens;

    /**
     * 补全信息消耗 tokens
     */
    private  Long completionTokens;


    /**
     * 余额变化量
     */
    private BigDecimal balance;
}
