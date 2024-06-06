package com.cn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *  修改用户信息DTO
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class UpdateUserInfoDto implements Serializable {

    @NotNull(message = "用户标识不能为空")
    private Long userId;

    @NotNull(message = "过期时间不能为空")
    private LocalDateTime expirationTime;

    @NotBlank(message = "类型")
    private String type;

    @NotBlank(message = "呢称不能为空")
    private String nickName;

    @NotNull(message = "余额不能为空")
    private BigDecimal accountBalance;

    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;


}
