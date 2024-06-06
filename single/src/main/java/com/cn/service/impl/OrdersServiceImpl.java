package com.cn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.dto.Orders.DeleteOrdersDto;
import com.cn.entity.TsOrders;
import com.cn.entity.TsUser;
import com.cn.mapper.TsOrdersMapper;
import com.cn.mapper.TsUserMapper;
import com.cn.service.OrdersService;
import com.cn.utils.StringUtils;
import com.cn.vo.OrdersPageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 订单业务层
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@SuppressWarnings("all")
@Slf4j
public class OrdersServiceImpl implements OrdersService {

    private final TsOrdersMapper tsOrdersMapper;

    private final TsUserMapper tsUserMapper;

    @Override
    public IPage<OrdersPageVo> getOrdersPage(int pageNum, String prompt) {

        return tsOrdersMapper.selectPage(new Page<>(pageNum, 15), new QueryWrapper<TsOrders>().lambda().eq(StringUtils.notEmpty(prompt), TsOrders::getOrdersId, prompt).orderByDesc(TsOrders::getCreatedTime)).convert(s -> {
            final TsUser tsUser = tsUserMapper.selectOne(new QueryWrapper<TsUser>().lambda().eq(TsUser::getUserId, s.getUserId()).select(TsUser::getPhoneNumber));

            return new OrdersPageVo().setPhoneNumber(tsUser.getPhoneNumber()).setOrdersId(s.getOrdersId()).setDays(s.getDays()).setCreatedTime(s.getCreatedTime()).setUpdateTime(s.getUpdateTime()).setPrice(s.getPrice().doubleValue()).setProductName(s.getProductName()).setStatus(s.getStatus());

        });

    }

    @Override
    public void deleteOrders(DeleteOrdersDto dto) {
        tsOrdersMapper.delete(new QueryWrapper<TsOrders>()
                .lambda()
                .eq(TsOrders::getOrdersId, dto.getOrdersId())
        );
    }
}
