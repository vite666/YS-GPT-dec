package com.cn.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.entity
 * @Author: 刘志威
 * @CreateTime: 2024-05-22  14:48
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
@TableName(value = "product_recharge")
public class ProductRecharge{

    /**
     * 商品 ID
     */
    @TableId(type = IdType.AUTO)
    private Long productRechargeId;

    /**
     * 商品名称
     */
    private String productName;


    /**
     * 充值余额
     */
    private BigDecimal rechargeQuota;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
