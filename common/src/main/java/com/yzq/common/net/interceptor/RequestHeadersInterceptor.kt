package com.yzq.common.net.interceptor

import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.LogUtils
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



        LogUtils.i("RequestHeadersInterceptor")
        var request = chain.request()


        request = request.newBuilder()
                .addHeader(ServerConstants.DEVICE_ID, DeviceUtils.getAndroidID())
                //   .header("Content-Type", "text/plain; charset=utf-8")
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip")
                .header("Cache-Control", "no-cache")
                .build()


        return chain.proceed(request)

    }


}