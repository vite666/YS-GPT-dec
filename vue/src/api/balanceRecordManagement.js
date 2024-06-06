import request from "@/utils/Request";


/**
 * 分页获取 使用记录信息
 * @param pageNum
 * @param prompt
 * @param userID
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function reqGetBalanceRecordsPage(pageNum, prompt, userID) {
    return request({
        url: '/balanceRecords-management/get/balanceRecords/page?pageNum=' + pageNum + '&prompt=' + prompt + '&userId=' + userID, method: 'GET'
    })
}


