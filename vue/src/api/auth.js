import request from "@/utils/Request";

/**
 *   邮箱登录
 */
export function reqEmailLogin(data) {
    return request({
        url: '/auth/email/login', method: 'POST', data
    })
}


/**
 *   获取邮箱验证码
 */
export function reqSendEmailCode(data) {
    return request({
        url: '/email/send/code', method: 'POST', data
    })
}
/**
 *   获取当前登录用户信息
 */
export function reqGetCurrentUserInfo() {
    return request({
        url: '/user/get/userinfo', method: 'GET'
    })
}


/**
 * 发送手机短信验证码
 * @param data
 * @returns {*}
 */
export function reqSMScode(data) {
    return request({
        url: '/phoneNumber/send/code', method: 'POST', data
        })
}

/**
 * 手机号 + 密码 登录
 * @param data
 * @returns {*}
 */
export function reqPasswordLogin(data) {
    return request({
        url: '/auth/passsword/login', method: 'POST', data
    })
}

/**
 * 手机号 注册
 * @param data
 * @returns {*}
 */
export function reqPhoneNumberRegister(data) {
    return request({
        url: '/auth/phoneNumber/register', method: 'POST', data
    })
}




/**
 * 更新头像
 */
export function reqLogout() {
    return request({
        url: '/auth/sign/logout',
        method: 'POST'
    })
}


/**
 *   更新昵称
 */
export function reqUploadNickName(data) {
    return request({
        url: '/user/upload/nickname',
        method: 'POST',
        data
    })
}

/**
 * 更新头像
 * @returns {string}
 */
export function reqUploadAvatar() {
    return '/user/upload/avatar'
}

