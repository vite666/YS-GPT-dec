package com.cn.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.dto
 * @Author: 刘志威
 * @CreateTime: 2024-05-21  14:55
 * @Description: 手机号+密码登录DTO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
public class PasswordLoginDto {
    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;

    @NotBlank(message = "密码不能为空")
    private String password;
}
