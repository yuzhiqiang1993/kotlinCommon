package com.yzq.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @description 流量拦截器
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

class TrafficInterceptor : Interceptor {

    companion object {
        private const val TAG = "TrafficInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val originalResponse = chain.proceed(originalRequest)
        if (originalResponse.body != null) {
            val newResp = originalResponse.newBuilder()
                .body(TrafficResponseBody(originalRequest.url.toString(), originalResponse.body!!))
                .build()
            return newResp
        }
        return originalResponse
    }
}