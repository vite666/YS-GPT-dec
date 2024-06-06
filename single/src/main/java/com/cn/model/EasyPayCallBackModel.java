package com.cn.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.model
 * @Author: 刘志威
 * @CreateTime: 2024-05-24  00:06
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class EasyPayCallBackModel {


    /**
     * 商户id
     */
    private Integer pid;

    /**
     * 易支付订单号
     */
    private String trade_no;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 支付方式
     */
    private String type;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品金额
     */
    private String money;

    /**
     * 支付状态只有TRADE_SUCCESS是成功
     */
    private String trade_status;

    /**
     * 业务扩展参数
     */
    private String param;

    /**
     * 签名字符串
     */
    private String sign;

    /**
     * 签名类型
     */
    private String sign_type;


}
