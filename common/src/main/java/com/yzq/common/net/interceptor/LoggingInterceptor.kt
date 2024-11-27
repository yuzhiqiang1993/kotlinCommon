package com.yzq.common.net.interceptor

import com.yzq.common.BuildConfig
import com.yzq.logger.Logger
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset

/**
 * @description 网络请求日志拦截
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

class LoggingInterceptor : Interceptor {

    companion object {
        private const val TAG = "LoggingInterceptor"
    }

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
        val requestSb = StringBuilder().appendLine("请求URL：${request.url}")
            .appendLine("请求方法：${request.method}").appendLine("请求头：")
            .appendLine(request.headers).appendLine("请求体:").appendLine(requestContent)
        Logger.it(TAG, requestSb.toString())


        val response = chain.proceed(request)

        val respSb = StringBuilder().appendLine("响应URL：${response.request.url}")
            .appendLine("响应码：${response.code}").appendLine("响应头:")
            .appendLine(response.headers)

        // 检查响应体的内容类型
        val contentType = response.body?.contentType()
        //如果是文本类型的响应体，打印响应体
        val logRespContent =
            contentType?.type == "text" || contentType?.subtype == "json" || contentType?.subtype == "xml" || contentType?.subtype == "html"

        if (logRespContent) {
            // 处理文本响应体
            val responseContent =
                runCatching {
                    response.peekBody(Long.MAX_VALUE).string()
                }.getOrDefault("响应数据获取异常")
            // 打印响应信息
            respSb.appendLine("响应体").appendLine(responseContent)
        } else {
            respSb.appendLine("响应体：[非文本类型]")
        }

        if (response.code != 200) {
            Logger.wt(TAG, respSb.toString())
        } else {
            Logger.it(TAG, respSb.toString())
        }

        return response
    }
}