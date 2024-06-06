package com.cn.controller;

import com.cn.msg.Result;
import com.cn.service.GptModelConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.controller
 * @Author: 刘志威
 * @CreateTime: 2024-05-23  15:48
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/gpt-user")
@RequiredArgsConstructor
public class GptModelContorller {
    private final GptModelConfigService gptModelConfigService;

    @GetMapping(value = "/get/gpt-model/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getGptModelPage(@RequestParam(defaultValue = "1") int pageNum, @RequestParam String prompt) {
        return Result.data(gptModelConfigService.getGptModelPage(pageNum, prompt));
    }
}
