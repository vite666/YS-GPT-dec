package com.cn.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName(value = "ts_gpt_model")
@Accessors(chain = true)
public class TsGptModel {

    @TableId(type = IdType.AUTO)
    private Long gptModelId;

    private String modelName;

    private Integer isSeniorModel;

    private Double topP;

    private Long maxTokens;

    private Double temperature;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private BigDecimal promptPrice;

    private BigDecimal completionPrice;

    /**
     * 模型描述
     */
    private String content;
}
