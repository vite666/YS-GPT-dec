package com.cn.service.impl;


import com.cn.constant.PhoneNumberConstant;
import com.cn.exception.EmailException;
import com.cn.service.PhoneNumberService;
import com.cn.utils.HttpUtils;
import com.cn.utils.RedisUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.service.impl
 * @Author: 刘志威
 * @CreateTime: 2024-05-21  10:04
 * @Description: 手机号 业务实现类
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
@SuppressWarnings("all")
@Slf4j
public class PhoneNumberServiceImpl implements PhoneNumberService {


    private final RedisUtils redisUtils;

    @Value(value = "${spring.sms.host}")
    private String host;

    @Value(value = "${spring.sms.path}")
    private String path;

    @Value(value = "${spring.sms.appcode}")
    private String appcode;

    @Value(value = "${spring.sms.smsSignId}")
    private String smsSignId;

    @Value(value = "${spring.sms.templateId}")
    private String templateId;


    @Override
    public void getSMSCode(String phoneNumber) {

        final String code = RandomStringUtils.randomNumeric(6);

        Context context = new Context();
        context.setVariable("code", code);

        Map<String, String> headers = new HashMap<String, String>();

        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phoneNumber);

        querys.put("param", "**code**:" + code + ",**minute**:5");

        //smsSignId（短信前缀）和templateId（短信模板），可登录国阳云控制台自助申请。参考文档：http://help.guoyangyun.com/Problem/Qm.html

        querys.put("smsSignId", smsSignId);
        querys.put("templateId", templateId);
        Map<String, String> bodys = new HashMap<String, String>();

        try {
            HttpResponse response = HttpUtils.doPost(host, path, "POST", headers, querys, bodys);
            redisUtils.setValueTimeout(PhoneNumberConstant.CAPTCHA_CODE + phoneNumber, code, 300);
//            System.out.println(response.toString());
//            //获取response的body
//            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (MessagingException e) {
            log.error("获取短信验证码失败 信息:{}, 位置", e.getMessage(), e.getClass());
            throw new EmailException("获取验证码失败!请稍后再试!", 500);
        } catch (Exception e) {
            throw new EmailException("手机号不存在，请稍后再试", 500);
        }
    }


}
