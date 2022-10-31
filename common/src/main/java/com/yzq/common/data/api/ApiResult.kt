package com.yzq.common.data.api

import com.tencent.bugly.proguard.T


/**
 * @description Api接口相关的密封类
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/10/28
 * @time    14:29
 */

sealed class ApiResult<out T> {
    /**
     * 表示请求成功且接受到了正确响应码的响应结果
     *
     * @param T
     * @property data
     * @constructor Create empty Success
     */
    data class Success<out T>(val data: T?) : ApiResult<T>()

    /**
     * 表示请求正常(respCode：200),接收到了包含错误码的响应结果
     *
     * @param T
     * @property code
     * @property message
     * @constructor Create empty Error
     */
    data class Error(val code: Int, val message: String = "") : ApiResult<Nothing>()


    /**
     * 表示请求异常了，例如网络异常，json解析异常，io异常以及其他未知异常等
     *
     * @property t
     * @constructor Create empty Exception
     */
    data class Exception(val t: Throwable) : ApiResult<Nothing>()
}


inline fun <reified T> ApiResult<T>.onSuccess(success: (T?) -> Unit) {
    if (this is ApiResult.Success) {
        success(data)
    }
}

inline fun <T> ApiResult<T>.onFailed(error: (code: Int, message: String) -> Unit) {
    if (this is ApiResult.Error) {
        error(code, message)
    }
}

inline fun <T> ApiResult<T>.onException(exception: (t: Throwable) -> Unit) {
    if (this is ApiResult.Exception) {
        exception(t)
    }
}