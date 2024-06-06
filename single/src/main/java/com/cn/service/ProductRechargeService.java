package com.cn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cn.dto.ProductCard.AddProductCardDto;
import com.cn.dto.ProductCard.DeleteProductCardDto;
import com.cn.dto.ProductCard.UpdateProductCardDto;
import com.cn.dto.ProductRecharge.AddProductRechargeDto;
import com.cn.dto.ProductRecharge.DeleteProductRechargeDto;
import com.cn.dto.ProductRecharge.UpdateProductRechargeDto;
import com.cn.vo.ProductCardPageVo;
import com.cn.vo.ProductRechargePageVo;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.service
 * @Author: 刘志威
 * @CreateTime: 2024-05-22  15:19
 * @Description: TODO
 * @Version: 1.0
 */
public interface ProductRechargeService {

    /**
     * 分页获取商品信息
     *
     * @param pageNum the page num
     * @param prompt  the prompt
     * @return the product card page
     */

    IPage<ProductRechargePageVo> getProductRechargePage(final int pageNum, final String prompt);

    /**
     * 删除指定商品
     *
     * @param dto the dto
     */
    void deleteProductRecharge(DeleteProductRechargeDto dto);

    /**
     * 修改指定商品
     *
     * @param dto the dto
     */
    void updateProductRecharge(UpdateProductRechargeDto dto);

    /**
     * 新增一个商品
     *
     * @param dto the dto
     */
    void addProductRecharge(AddProductRechargeDto dto);
}
