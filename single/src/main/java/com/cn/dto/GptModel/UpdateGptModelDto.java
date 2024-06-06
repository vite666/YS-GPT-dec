package com.cn.dto.GptModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 修改指定gpt模型 DTO
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class UpdateGptModelDto implements Serializable {

    @NotNull(message = "模型标识不能为空")
    private Long gptModelId;

    @NotNull(message = "作用域不能为空")
    private Boolean isSeniorModel;

    @NotBlank(message = "模型名称不能为空")
    private String modelName;

    @NotNull(message = "top_p不能为空")
    private Double topP;

    @NotNull(message = "max_tokens不能为空")
    private Long maxTokens;

    @NotNull(message = "temperature不能为空")
    private Double temperature;

    private BigDecimal promptPrice;

    private BigDecimal completionPrice;

    private String content;
}
