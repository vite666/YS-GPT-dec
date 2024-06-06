import request from "@/utils/Request";

/**
 *   分页获取产品
 */
export function reqGetProductPage(pageNum, prompt) {
    return request({
        url: '/product-management/get/product/page?pageNum=' + pageNum + '&prompt=' + prompt, method: 'GET'
    })
}

/**
 * 分页获取 余额充值 商品
 * @param pageNum
 * @param prompt
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function reqGetProductRechargePage(pageNum, prompt) {
    return request({
        url: '/product-management/get/product/recharge/page?pageNum=' + pageNum + '&prompt=' + prompt, method: 'GET'
    })
}

/**
 *   新增产品
 */
export function reqAddProduct(data) {
    return request({
        url: '/product-management/add/product', method: 'POST', data
    })
}

/**
 * 新增余额充值商品
 * @param data
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function reqAddProductRecharge(data) {
    return request({
        url: '/product-management/add/product/recharge', method: 'POST', data
    })
}

/**
 *   修改产品
 */
export function reqUpdateProduct(data) {
    return request({
        url: '/product-management/update/product', method: 'POST', data
    })
}

/**
 * 修改 余额充值 商品
 * @param data
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function reqUpdateProductRecharge(data) {
    return request({
        url: '/product-management/update/product/recharge', method: 'POST', data
    })
}

/**
 * 删除头像
 */
export function reqDeleteProduct(data) {
    return request({
        url: '/product-management/delete/product', method: 'POST', data
    })
}

/**
 * 删除 余额充值 商品
 * @param data
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function reqDeleteProductRecharge(data) {
    return request({
        url: '/product-management/delete/product/recharge', method: 'POST', data
    })
}

