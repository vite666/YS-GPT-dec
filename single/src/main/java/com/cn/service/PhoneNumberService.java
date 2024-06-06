package com.cn.service;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.service
 * @Author: 刘志威
 * @CreateTime: 2024-05-21  10:03
 * @Description: 手机号业务层
 * @Version: 1.0
 */
public interface PhoneNumberService {

    /**
     * 获取短信验证码
     * @param email
     */
    void getSMSCode(final String email);
}
