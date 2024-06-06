package com.cn.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.model
 * @Author: 刘志威
 * @CreateTime: 2024-05-23  21:50
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class EasyPayConfigModel {
    /**
     * 订单id
     */
    private String outTradeNo;

    /**
     * 商户id
     */
    private Integer pid;

    /**
     * 商户密钥
     */
    private String key;

    /**
     * 商品名
     */
    private String name;

    /**
     * 价格
     */
    private String money;

    /**
     * 加密方式
     */
    private String signType = "MD5";

    /**
     * 支付方式
     */
    private String type ;

    /**
     * 签名字符串
     */
    private String sign ;

    /**
     * 请求地址
     */
    private String url ;

    /**
     * 异步通知地址
     */
    private String notifyUrl ;

    /**
     * 跳转通知地址
     */
    private String returnUrl ;

}
