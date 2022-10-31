package com.yzq.common.ext

import com.yzq.common.data.api.ApiResult
import com.yzq.common.data.api.BaseResp
import com.yzq.common.net.constants.ResponseCode
import retrofit2.Response


/**
 * @description 返回值类型是BaseResp 的请求
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/10/28
 * @time    16:20
 */
suspend inline fun <T> baseRespApiCall(crossinline requestMethod: suspend () -> BaseResp<T>): ApiResult<T> {
    return try {
        val resp = requestMethod.invoke()
        if (resp.code == ResponseCode.SUCCESS) {
            ApiResult.Success(resp.data)
        } else {
            ApiResult.Error(resp.code, resp.message)
        }
    } catch (t: Throwable) {
        ApiResult.Exception(t)
    }
}


/**
 * @description 通用的请求封装 针对一些非内部服务的接口，一般该类接口跟内部服务接口返回的数据格式不一致
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/10/28
 * @time    16:20
 */
suspend inline fun <T> apiCall(crossinline requestMethod: suspend () -> Response<T>): ApiResult<T> {
    return try {
        val resp = requestMethod.invoke()
        if (resp.isSuccessful) {
            if (resp.body() == null) {
                ApiResult.Error(-1, "ResponseBody is null")
            } else {
                ApiResult.Success(resp.body())
            }
        } else {
            ApiResult.Error(resp.code(), resp.message())
        }

    } catch (t: Throwable) {
        ApiResult.Exception(t)
    }
}

