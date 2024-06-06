package com.cn.dto.DallKey;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class AddDallKeyDto {

    @NotBlank(message = "密钥不能为空")
    private String openAiKey;
}
