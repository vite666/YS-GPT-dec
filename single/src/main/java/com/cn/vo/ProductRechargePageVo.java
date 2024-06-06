package com.cn.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.vo
 * @Author: 刘志威
 * @CreateTime: 2024-05-22  15:20
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
public class ProductRechargePageVo {
    /**
     * 商品ID
     */
    private Long productRechargeId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 充值余额
     */
    private BigDecimal rechargeQuota;

    private LocalDateTime createdTime;

    private LocalDateTime updateTime;
}
