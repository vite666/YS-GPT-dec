package com.cn.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.vo
 * @Author: 刘志威
 * @CreateTime: 2024-05-23  21:11
 * @Description: 易支付 创建订单号视图
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
public class OrdersEasyPayCodeVo {
    /**
     * 支付链接，前端跳转到该链接支付
     */
    private String url;
}
