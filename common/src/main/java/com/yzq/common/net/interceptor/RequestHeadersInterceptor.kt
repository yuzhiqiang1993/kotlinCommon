package com.yzq.common.net.interceptor

import com.blankj.utilcode.util.DeviceUtils
import com.yzq.common.constants.ServerConstants
import okhttp3.Interceptor
import okhttp3.Response


/**
 * @description: 定义请求头信息
 * @author : yzq
 * @date   : 2019/3/16
 * @time   : 16:37
 *
 */

class RequestHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {


        val request = chain.request()

        val builder = request.newBuilder()

        builder.addHeader(ServerConstants.DEVICE_ID, DeviceUtils.getAndroidID())
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip")
                .header("Cache-Control", "no-cache")
                .build()


        return chain.proceed(builder.build())

    }


}