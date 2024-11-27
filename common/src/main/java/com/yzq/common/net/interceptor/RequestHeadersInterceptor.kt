package com.yzq.common.net.interceptor

import android.os.Build
import com.yzq.application.AppManager
import com.yzq.application.getAppVersionCode
import com.yzq.application.getAppVersionName
import com.yzq.base.extend.getAndroidId
import com.yzq.base.extend.getAndroidVersion
import com.yzq.base.extend.getDeviceModel
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


        val httpAgent = System.getProperty("http.agent")

        builder.header("Device-ID", getAndroidId())
            .header("Android-Version", getAndroidVersion())
            .header("Version-Code", "${AppManager.getAppVersionCode()}")
            .header("Version-Name", AppManager.getAppVersionName())
            .header("Device", "${Build.BRAND}/${getDeviceModel()}")
            .header("Accept", "*/*").header("Accept-Encoding", "gzip")
            .header("User-Agent", httpAgent ?: "").header("Cache-Control", "no-cache").build()


        return chain.proceed(builder.build())

    }


}