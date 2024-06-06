package com.cn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.entity.BalanceRecords;
import com.cn.entity.TsGptModel;
import com.cn.entity.TsOrders;
import com.cn.entity.TsUser;
import com.cn.mapper.BalanceRecordMapper;
import com.cn.service.BalanceRecordsService;
import com.cn.service.GptModelConfigService;
import com.cn.service.UserService;
import com.cn.utils.StringUtils;
import com.cn.vo.BalanceRecordsVo;
import com.cn.vo.OrdersPageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.service.impl
 * @Author: 刘志威
 * @CreateTime: 2024-05-20  22:50
 * @Description: 余额记录 业务实现类
 * @Version: 1.0
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceRecordsServiceImpl implements BalanceRecordsService {

    private final BalanceRecordMapper balanceRecordMapper;

    private final GptModelConfigService gptModelConfigService;

    private final UserService userService;

    @Override
    public IPage<BalanceRecordsVo> getBalanceRecordsPage(int pageNum, String prompt, int userId) {

        return balanceRecordMapper.selectPage(new Page<>(pageNum, 15), new QueryWrapper<BalanceRecords>()
                .lambda().eq(BalanceRecords::getUserId, userId)
                        .orderByDesc(BalanceRecords::getCreatedTime).eq(StringUtils.notEmpty(prompt), BalanceRecords::getType, prompt))
                .convert(s -> {
                    final TsUser tsUser = userService.getUserById(s.getUserId());
                    return new BalanceRecordsVo().setCreatedTime(s.getCreatedTime())
                            .setType(s.getType())
                            .setBalance(s.getBalance())
                            .setModelName(s.getModelName())
                            .setPromptTokens(s.getPromptTokens())
                            .setCompletionTokens(s.getCompletionTokens())
                            .setUserPhoneNumber(tsUser.getPhoneNumber());
        });
    }
    @Override
    public IPage<BalanceRecordsVo> getBalanceRecordsPage(int pageNum, String prompt) {
        return balanceRecordMapper.selectPage(new Page<>(pageNum, 15), new QueryWrapper<BalanceRecords>()
                .lambda().orderByDesc(BalanceRecords::getCreatedTime).eq(StringUtils.notEmpty(prompt), BalanceRecords::getType, prompt)).convert(s -> {
            final TsUser tsUser = userService.getUserById(s.getUserId());
            return new BalanceRecordsVo().setCreatedTime(s.getCreatedTime())
                    .setType(s.getType())
                    .setBalance(s.getBalance())
                    .setModelName(s.getModelName())
                    .setPromptTokens(s.getPromptTokens())
                    .setCompletionTokens(s.getCompletionTokens())
                    .setUserPhoneNumber(tsUser.getPhoneNumber());
        });
    }


    @Override
    public void addBalanceRecords(BalanceRecords balanceRecords) {
        balanceRecordMapper.insert(balanceRecords);
    }


}
