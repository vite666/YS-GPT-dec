package com.cn.controller.admin;

import com.cn.dto.ProductCard.AddProductCardDto;
import com.cn.dto.ProductCard.DeleteProductCardDto;
import com.cn.dto.ProductCard.UpdateProductCardDto;
import com.cn.dto.ProductRecharge.AddProductRechargeDto;
import com.cn.dto.ProductRecharge.DeleteProductRechargeDto;
import com.cn.dto.ProductRecharge.UpdateProductRechargeDto;
import com.cn.entity.ProductRecharge;
import com.cn.msg.Result;
import com.cn.service.ProductCardService;
import com.cn.service.ProductRechargeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 商品管理 API
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/product-management")
@RequiredArgsConstructor
public class ProductManagementController {


    private final ProductCardService productCardService;
    private final ProductRechargeService productRechargeService;

    /**
     * 分页获取商品信息
     *
     * @param pageNum the page num
     * @param prompt  the prompt
     * @return the product page
     */
    @GetMapping(value = "/get/product/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getProductPage(@RequestParam(defaultValue = "1") final int pageNum, @RequestParam final String prompt) {

        return Result.data(productCardService.getProductCardPage(pageNum, prompt));

    }

    /**
     * 分页获取 余额充值 商品信息
     * @param pageNum
     * @param prompt
     * @return
     */
    @GetMapping(value = "/get/product/recharge/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getProductRechargePage(@RequestParam(defaultValue = "1") final int pageNum, @RequestParam final String prompt) {

        return Result.data(productRechargeService.getProductRechargePage(pageNum, prompt));

    }


    /**
     * email account login
     */
    @PostMapping(value = "/delete/product/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result deleteProduct(@RequestBody @Validated DeleteProductCardDto dto) {
        productCardService.deleteProductCard(dto);
        return Result.ok();
    }

    /**
     * 删除 余额充值 商品
     * @param dto
     * @return
     */
    @PostMapping(value = "/delete/product/recharge", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result deleteProductRecharge(@RequestBody @Validated DeleteProductRechargeDto dto) {
        productRechargeService.deleteProductRecharge(dto);
        return Result.ok();
    }

    /**
     * email account login
     */
    @PostMapping(value = "/add/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result addProduct(@RequestBody @Validated AddProductCardDto dto) {
        productCardService.addProductCard(dto);
        return Result.ok();
    }

    /**
     * 新增 余额充值 商品
     * @param dto
     * @return
     */
    @PostMapping(value = "/add/product/recharge", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result addProductRecharge(@RequestBody @Validated AddProductRechargeDto dto) {
        productRechargeService.addProductRecharge(dto);
        return Result.ok();
    }

    /**
     * 更新 余额充值 商品
     * @param dto
     * @return
     */
    @PostMapping(value = "/update/product/recharge", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result updateProductRecharge(@RequestBody @Validated UpdateProductRechargeDto dto) {
        productRechargeService.updateProductRecharge(dto);
        return Result.ok();
    }

    /**
     * Update user result.
     *
     * @param dto the dto
     * @return the result
     */
    @PostMapping(value = "/update/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result updateProduct(@RequestBody @Validated UpdateProductCardDto dto) {
        productCardService.updateProductCard(dto);
        return Result.ok();


    }


}
