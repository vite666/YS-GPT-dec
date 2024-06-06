package com.cn.dto.DallKey;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class UpdateDallKeyDto {

    @NotNull(message = "标识不能为空")
    private Long dallKeyId;

    @NotBlank(message = "密钥不能为空")
    private String openAiKey;
}
