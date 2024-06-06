package com.cn.utils;

import com.cn.model.EasyPayCallBackModel;
import com.cn.model.EasyPayConfigModel;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.utils
 * @Author: 刘志威
 * @CreateTime: 2024-05-23  22:14
 * @Description: 易支付相关工具类
 * @Version: 1.0
 */
@Component
@SuppressWarnings("all")
public class EasyPayUtil {

    /**
     * 生成请求易支付签名
     * @param model
     * @return
     */
    public static String createSign(EasyPayConfigModel model){
        Map<String,String> sign = new HashMap<>();
        sign.put("pid",model.getPid().toString());
        sign.put("type",model.getType());
        sign.put("out_trade_no",model.getOutTradeNo());
        sign.put("notify_url",model.getNotifyUrl());
        sign.put("return_url",model.getReturnUrl());
        sign.put("name",model.getName());
        sign.put("money",model.getMoney());
        sign = sortByKey(sign);
        //遍历map 转成字符串
        String signStr = "";
        for(Map.Entry<String,String> m :sign.entrySet()){
            signStr += m.getKey() + "=" +m.getValue()+"&";
        }
        //去掉最后一个 &
        signStr = signStr.substring(0,signStr.length()-1);
        //最后拼接上KEY
        signStr += model.getKey();
        //转为MD5
        try {
            signStr = DigestUtils.md5DigestAsHex(signStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return signStr;
    }

    /**
     * 生成回调易支付签名
     * @param easyPayCallBackModel
     * @return
     */
    public static String createSign(EasyPayCallBackModel model, String key){
        Map<String,String> sign = new HashMap<>();
        sign.put("pid",model.getPid().toString());
        sign.put("trade_no", model.getTrade_no());
        sign.put("type",model.getType());
        sign.put("out_trade_no",model.getOut_trade_no());
        sign.put("name", model.getName());
        sign.put("money", model.getMoney());
        sign.put("trade_status", model.getTrade_status());

        sign = sortByKey(sign);
        //遍历map 转成字符串
        String signStr = "";
        for(Map.Entry<String,String> m :sign.entrySet()){
            signStr += m.getKey() + "=" +m.getValue()+"&";
        }
        //去掉最后一个 &
        signStr = signStr.substring(0,signStr.length()-1);
        //最后拼接上KEY
        signStr += key;
        //转为MD5
        signStr = DigestUtils.md5DigestAsHex(signStr.getBytes());
        return signStr;
    }
    public static  <K extends Comparable<? super K>, V > Map<K, V> sortByKey(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.<K, V>comparingByKey()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }
}
