package com.cn.controller;

import com.cn.dto.MjTaskDto;
import com.cn.exception.DrawingException;
import com.cn.exceptions.MemberException;
import com.cn.msg.Result;
import com.cn.service.MjService;
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
 * @CreateTime: 2024-05-27  23:50
 * @Description: Mj 绘图控制层
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/mj")
@RequiredArgsConstructor
public class MjController {


    private final MjService mjService;

    @PostMapping(value = "/push/generate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result pushGenerateTask(@RequestBody @Validated MjTaskDto dto) {
        try {
            return Result.data(mjService.addMjDrawingTask(dto));
        } catch (DrawingException | MemberException e) {
            return Result.error(e.getMessage());
        }
    }

}
