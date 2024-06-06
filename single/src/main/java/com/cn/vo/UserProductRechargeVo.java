package com.cn.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.vo
 * @Author: 刘志威
 * @CreateTime: 2024-05-24  01:28
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
public class UserProductRechargeVo {

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
     * 所含额度
     */
    private BigDecimal rechargeQuota;

    /**
     * 是否选中
     */
    private Boolean isSelected = false;
}
