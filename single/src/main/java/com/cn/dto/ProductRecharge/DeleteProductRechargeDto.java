package com.cn.dto.ProductRecharge;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

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
public class DeleteProductRechargeDto {
    @NotNull(message = "标识不能为空")
    private Long productRechargeId;
}
