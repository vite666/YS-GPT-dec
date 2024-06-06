package com.cn.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.constant.EmailConstant;
import com.cn.constant.UserCacheConstant;
import com.cn.dto.Email.EmailLoginDto;
import com.cn.dto.PasswordLoginDto;
import com.cn.dto.PhoneNumber.PhoneNumberRegisterDto;
import com.cn.entity.TsUser;
import com.cn.exception.AuthException;
import com.cn.exception.EmailException;
import com.cn.mapper.TsUserMapper;
import com.cn.service.AuthService;
import com.cn.structure.UserInfoStructure;
import com.cn.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import java.math.BigDecimal;

import static com.cn.constant.StatisticsConstant.*;

/**
 * 登录类 业务实现
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@SuppressWarnings("all")
@Slf4j
public class AuthServiceImpl implements AuthService {


    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    private final TsUserMapper tsUserMapper;

    private final RedisUtils redisUtils;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String emailAuthLogin(final EmailLoginDto dto) {
        final String code = dto.getCode();
        final String KEY = EmailConstant.CAPTCHA_CODE + dto.getEmail();
        //获取验证码
        final Object value = redisUtils.getValue(KEY);
        if (value != null && value.equals(code)) {
            TsUser user = tsUserMapper.selectOne(new QueryWrapper<TsUser>().lambda()
                    .eq(TsUser::getEmail, dto.getEmail())
                    .select(TsUser::getType,
                            TsUser::getUserId,
                            TsUser::getEmail,
                            TsUser::getOpenId,
                            TsUser::getAvatar,
                            TsUser::getExpirationTime,
                            TsUser::getNickName));
            //不存在则注册
            if (user == null) {
                //数据采集
                redisUtils.increment(NEW_USERS, 1);
                redisUtils.increment(NEW_VISITS, 1);
                user = new TsUser().setEmail(dto.getEmail());
                tsUserMapper.insert(user);
            } else {
                //数据采集
                redisUtils.increment(TOTAL_VISITS, 1);
            }
            StpUtil.login(user.getUserId());
            //构建用户缓存数据
            StpUtil.getSession()
                    //设置用户数据缓存
                    .set(UserCacheConstant.USER_INFO_DATA, new UserInfoStructure()
                            .setUserID(user.getUserId())
                            .setNickName(user.getNickName())
                            .setExpirationTime(user.getExpirationTime())
                            .setEmail(user.getEmail())
                            .setType(user.getType())
                            .setAvatar(user.getAvatar())
                            .setPhoneNumber(user.getPhoneNumber())
                            .setAccountBlance(user.getAccountBalance())
                            .setOpenId(user.getOpenId()));
            return StpUtil.getTokenValue();
        }
        throw new EmailException("邮箱验证码输入有误");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String passwordLogin(PasswordLoginDto dto) {
        final String phoneNumber = dto.getPhoneNumber();
        final String password = dto.getPassword();

        TsUser user = tsUserMapper.selectOne(new QueryWrapper<TsUser>().lambda()
                .eq(TsUser::getPhoneNumber, dto.getPhoneNumber())
                .select(TsUser::getType,
                        TsUser::getUserId,
                        TsUser::getEmail,
                        TsUser::getOpenId,
                        TsUser::getAvatar,
                        TsUser::getPassword,
                        TsUser::getAccountBalance,
                        TsUser::getExpirationTime,
                        TsUser::getNickName));
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthException("用户不存在或密码错误");
        }

        redisUtils.increment(TOTAL_VISITS, 1);
        StpUtil.login(user.getUserId());
        //构建用户缓存数据
        StpUtil.getSession()
                //设置用户数据缓存
                .set(UserCacheConstant.USER_INFO_DATA, new UserInfoStructure()
                        .setUserID(user.getUserId())
                        .setNickName(user.getNickName())
                        .setExpirationTime(user.getExpirationTime())
                        .setAccountBlance(user.getAccountBalance())
                        .setEmail(user.getEmail())
                        .setType(user.getType())
                        .setAvatar(user.getAvatar())
                        .setPhoneNumber(user.getPhoneNumber())
                        .setOpenId(user.getOpenId()));
        return StpUtil.getTokenValue();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String phoneNumberRegister(PhoneNumberRegisterDto dto) {
        final String code = dto.getCode();
        final String KEY = EmailConstant.CAPTCHA_CODE + dto.getPhoneNumber();
        //获取验证码
        final Object value = redisUtils.getValue(KEY);
        if (value != null && value.equals(code)) {
            TsUser user = tsUserMapper.selectOne(new QueryWrapper<TsUser>().lambda()
                    .eq(TsUser::getPhoneNumber, dto.getPhoneNumber())
                    .select(TsUser::getType,
                            TsUser::getUserId,
                            TsUser::getEmail,
                            TsUser::getOpenId,
                            TsUser::getAvatar,
                            TsUser::getAccountBalance,
                            TsUser::getExpirationTime,
                            TsUser::getNickName));
            //不存在则注册
            if (user == null) {
                //数据采集
                redisUtils.increment(NEW_USERS, 1);
                redisUtils.increment(NEW_VISITS, 1);
                user = new TsUser().setPhoneNumber(dto.getPhoneNumber()).setPassword(passwordEncoder.encode(dto.getPassword())).setAccountBalance(BigDecimal.valueOf(3.1415926));
                tsUserMapper.insert(user);
            } else {
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
                tsUserMapper.updateById(user);
                //数据采集
                redisUtils.increment(TOTAL_VISITS, 1);
            }
            StpUtil.login(user.getUserId());
            //构建用户缓存数据
            StpUtil.getSession()
                    //设置用户数据缓存
                    .set(UserCacheConstant.USER_INFO_DATA, new UserInfoStructure()
                            .setUserID(user.getUserId())
                            .setNickName(user.getNickName())
                            .setExpirationTime(user.getExpirationTime())
                            .setAccountBlance(user.getAccountBalance())
                            .setEmail(user.getEmail())
                            .setType(user.getType())
                            .setAvatar(user.getAvatar())
                            .setPhoneNumber(user.getPhoneNumber())
                            .setOpenId(user.getOpenId()));
            return StpUtil.getTokenValue();
        }
        throw new AuthException("验证码输入有误");
    }

    @Override
    public String wechatAuthorizedLogin(final String code) {
        return null;
    }

    @Override
    public void logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
        }
    }

}
