package com.cn.controller.admin;

import com.cn.msg.Result;
import com.cn.service.BalanceRecordsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.controller.admin
 * @Author: 刘志威
 * @CreateTime: 2024-05-23  01:16
 * @Description: TODO
 * @Version: 1.0
 */

@Slf4j
@RestController
@RequestMapping("/balanceRecords-management")
@RequiredArgsConstructor
public class BalanceRecordsController {


    private final BalanceRecordsService balanceRecordsService;

    /**
     * 分页查询 使用记录，userID = -1, 查全部，否则查对应 userId 的使用纪律
     * @param pageNum
     * @param prompt
     * @param userId
     * @return
     */
    @GetMapping(value = "/get/balanceRecords/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getBalanceRecordsPage(@RequestParam(defaultValue = "1") final int pageNum, @RequestParam String prompt, @RequestParam(defaultValue = "-1") final int userId) {
        if (userId == -1)
            Result.data(balanceRecordsService.getBalanceRecordsPage(pageNum, prompt));
        return Result.data(balanceRecordsService.getBalanceRecordsPage(pageNum, prompt, userId));
    }




}
