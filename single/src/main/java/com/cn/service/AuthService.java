package com.cn.service;


import com.cn.dto.Email.EmailLoginDto;
import com.cn.dto.PasswordLoginDto;
import com.cn.dto.PhoneNumber.PhoneNumberRegisterDto;

/**
 * 登录类 业务
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
public interface AuthService {


    /**
     * 邮箱+验证码登录
     *
     * @param dto the dto
     * @return the string
     */
    String emailAuthLogin(final EmailLoginDto dto);


    /**
     * 手机号+密码 登录
     * @param dto
     * @return the string
     */
    String passwordLogin(final PasswordLoginDto dto);

    /**
     * 手机号验证码登录（用户注册或修改密码）
     * @param dto
     * @return
     */
    String phoneNumberRegister(final PhoneNumberRegisterDto dto);

    /**
     * 微信扫码登录
     *
     * @param code the code
     * @return the string
     */
    String wechatAuthorizedLogin(final String code);


    /**
     * 退出登录.
     */
    void logout();
}
