package com.cn.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.dto
 * @Author: 刘志威
 * @CreateTime: 2024-05-22  03:03
 * @Description: 创建易支付 DTO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
public class CreatedPayEasyPayDto {
    @NotNull(message = "商品标识不能为空")
    private Long productRechargeId;

    // 支付方式
    private String type;
}
