package com.cn.dto.PhoneNumber;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.dto
 * @Author: 刘志威
 * @CreateTime: 2024-05-21  11:50
 * @Description: 获取手机短信验证码 DTO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
public class PhoneNumberCaptchaDto {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phoneNumber;

}
