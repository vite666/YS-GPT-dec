package com.cn.dto.ProductRecharge;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.dto.ProductRecharge
 * @Author: 刘志威
 * @CreateTime: 2024-05-22  15:14
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
public class AddProductRechargeDto {
    @NotBlank(message = "商品名称不能为空")
    private String productName;

    @NotNull(message = "充值余额不能为空")
    private BigDecimal rechargeQuota;

    @NotNull(message = "商品价格不能为空")
    private BigDecimal price;

}
