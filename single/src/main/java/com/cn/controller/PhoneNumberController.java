package com.cn.controller;


import com.cn.dto.PhoneNumber.PhoneNumberCaptchaDto;
import com.cn.exception.EmailException;
import com.cn.msg.Result;
import com.cn.service.PhoneNumberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.controller
 * @Author: 刘志威
 * @CreateTime: 2024-05-21  11:57
 * @Description: 手机号 相关 API
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/phoneNumber")
@RequiredArgsConstructor
public class PhoneNumberController {


    private final PhoneNumberService phoneNumberService;

    /**
     * 发送手机短信验证码
     * @param dto
     * @return
     */
    @PostMapping(value = "/send/code", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result sendSMSCode(@RequestBody @Validated PhoneNumberCaptchaDto dto) {
        try {
            phoneNumberService.getSMSCode(dto.getPhoneNumber());
            return Result.ok();
        } catch (EmailException ex) {
            return Result.error(ex.getMessage());
        }
    }
}
