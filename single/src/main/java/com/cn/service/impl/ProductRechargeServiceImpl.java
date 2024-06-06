package com.cn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.dto.ProductRecharge.AddProductRechargeDto;
import com.cn.dto.ProductRecharge.DeleteProductRechargeDto;
import com.cn.dto.ProductRecharge.UpdateProductRechargeDto;
import com.cn.entity.ProductRecharge;

import com.cn.mapper.ProductRechargeMapper;
import com.cn.service.ProductRechargeService;
import com.cn.utils.StringUtils;

import com.cn.vo.ProductRechargePageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.service.impl
 * @Author: 刘志威
 * @CreateTime: 2024-05-22  15:27
 * @Description: 充值余额 商品实现类
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
@SuppressWarnings("all")
@Slf4j
public class ProductRechargeServiceImpl implements ProductRechargeService {
    private final ProductRechargeMapper productRechargeMapper;
    @Override
    public IPage<ProductRechargePageVo> getProductRechargePage(int pageNum, String prompt) {
        return productRechargeMapper.selectPage(new Page<>(pageNum, 15), new QueryWrapper<ProductRecharge>()
                .lambda()
                .like(StringUtils.notEmpty(prompt), ProductRecharge::getProductName, prompt)
        ).convert(s -> {

            return new ProductRechargePageVo()
                    .setProductName(s.getProductName())
                    .setRechargeQuota(s.getRechargeQuota())
                    .setPrice(s.getPrice())
                    .setCreatedTime(s.getCreatedTime())
                    .setUpdateTime(s.getUpdateTime())
                    .setProductRechargeId(s.getProductRechargeId());
        });
    }

    @Override
    public void deleteProductRecharge(DeleteProductRechargeDto dto) {

        productRechargeMapper.delete(new QueryWrapper<ProductRecharge>()
                .lambda()
                .eq(ProductRecharge::getProductRechargeId, dto.getProductRechargeId())
        );
    }

    @Override
    public void updateProductRecharge(UpdateProductRechargeDto dto) {

        productRechargeMapper.updateById(new ProductRecharge()
                .setProductRechargeId(dto.getProductRechargeId())
                .setProductName(dto.getProductName())
                .setPrice(dto.getPrice())
                .setRechargeQuota(dto.getRechargeQuota())
        );
    }

    @Override
    public void addProductRecharge(AddProductRechargeDto dto) {

        productRechargeMapper.insert(new ProductRecharge()
                .setProductName(dto.getProductName())
                .setPrice(dto.getPrice())
                .setRechargeQuota(dto.getRechargeQuota())
        );
    }
}
