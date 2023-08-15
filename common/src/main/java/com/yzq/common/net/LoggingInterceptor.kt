package com.sfic.kfc.knight.net.interecptor

import com.yzq.common.BuildConfig
import com.yzq.logger.Logger
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.nio.charset.Charset


/**
 * @description 网络请求日志拦截
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!BuildConfig.DEBUG) {
            return chain.proceed(chain.request())
        }
        val request = chain.request()
        val requestBody = request.body
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        val requestContent = buffer.readString(Charset.defaultCharset())

        // 打印请求信息

        val requestSb = StringBuilder()
            .appendLine("请求URL：${request.url}")
            .appendLine("请求方法：${request.method}")
            .appendLine("请求头：${request.headers}")
            .appendLine("请求体：$requestContent")
        Logger.i(requestSb.toString())


        val response = chain.proceed(request)
        val responseBody = response.body
        val responseContent = responseBody?.string()
        // 打印响应信息
        val respSb = StringBuilder()
            .appendLine("响应URL：${response.request.url}")
            .appendLine("响应码：${response.code}")
            .appendLine("响应头：${response.headers}")
            .appendLine("响应体：$responseContent")
        Logger.i(respSb.toString())

        // 重新构建响应体，因为原始响应体已经被读取过一次
        val newResponseBody = responseBody?.contentType()
            ?.let { (responseContent ?: "").toResponseBody(it) }

        return response.newBuilder().body(newResponseBody).build()
    }
}
