package com.cn.service;

import com.cn.dto.CreatedPayAlipayDto;
import com.cn.dto.CreatedPayEasyPayDto;
import com.cn.model.EasyPayCallBackModel;
import com.cn.model.EasyPayConfigModel;
import com.cn.vo.OrdersEasyPayCodeVo;
import com.cn.vo.OrdersPayCodeVo;
import com.cn.vo.UserProductCardVo;
import com.cn.vo.UserProductRechargeVo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 商品支付下单 业务
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
public interface ProductPayService {

    /**
     * 获取所有商品信息
     *
     * @return the all product card
     */
    List<UserProductCardVo> getAllProductCard();

    /**
     * 获取所有 余额充值 商品
     * @return
     */
    List<UserProductRechargeVo> getAllProductRecharge();


    /**
     * 获取订单支付状态
     *
     * @param ordersId the orders id
     * @return the ali pay status
     */
    Boolean getOrdersPayStatus(final String ordersId);


    /**
     *  创建支付宝订单
     *
     * @param dto the dto
     * @return the alipay pay code vo
     */
    OrdersPayCodeVo createdAliPayOrders(final CreatedPayAlipayDto dto);

    /**
     * 创建 易支付 订单
     * @param dto 传入 商品ID 和 支付方式
     * @return 跳转支付的链接
     */
    OrdersEasyPayCodeVo createEasyPayOrders(final CreatedPayEasyPayDto dto);

    /**
     * 易支付回调
     * @param req
     * @return
     */
    public String easyPayPullback(final EasyPayCallBackModel req);


    /**
     * 支付宝回调
     *
     * @param request the request
     * @return the string
     */
    String alipayPullback(final HttpServletRequest request);


}
