package com.cn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cn.entity.BalanceRecords;
import com.cn.vo.BalanceRecordsVo;
import com.cn.vo.OrdersPageVo;

/**
 * 余额记录 业务
 */
public interface BalanceRecordsService {


    /**
     * 分页获取 余额记录
     * @param pageNum
     * @param prompt
     * @return
     */
    IPage<BalanceRecordsVo> getBalanceRecordsPage(final int pageNum, final String prompt, int userId);


    public IPage<BalanceRecordsVo> getBalanceRecordsPage(int pageNum, String prompt);

    /**
     * 新增余额记录
     * @param balanceRecords
     */
    void addBalanceRecords(BalanceRecords balanceRecords);





}
