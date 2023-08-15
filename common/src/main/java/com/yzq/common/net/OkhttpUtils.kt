package com.sfic.kfc.merchant.network

import com.sfic.kfc.knight.net.interecptor.LoggingInterceptor
import com.yzq.base.utils.MoshiUtils
import com.yzq.coroutine.safety_coroutine.withIO
import com.yzq.logger.Logger
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit


/**
 * @description 封装的网络请求工具类
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

object OkhttpUtils {

    val client = OkHttpClient
        .Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(LoggingInterceptor())
        .build()

    /**
     * Get
     *
     * @param T 响应数据的类型
     * @param url 请求地址
     * @param params 参数
     * @return
     */
    suspend inline fun <reified T> get(
        url: String,
        params: Map<String, String> = emptyMap(),
    ): Result<T> {
        val builder = url.toHttpUrl().newBuilder()
        params.forEach { builder.addQueryParameter(it.key, it.value) }
        val request = Request.Builder()
            .url(builder.build())
            .build()
        return request<T>(request)
    }


    /**
     * Post
     *
     * @param T 响应数据的类型
     * @param url 请求地址
     * @param params 参数
     * @return
     */
    suspend inline fun <reified T> post(
        url: String,
        params: Map<String, String> = emptyMap(),
    ): Result<T> {
        val builder = url.toHttpUrl().newBuilder()
        val jsonStr = MoshiUtils.toJson(params)
        val requestBody = jsonStr.toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(builder.build())
            .post(requestBody)
            .build()
        return request<T>(request)
    }


    suspend inline fun <reified T> request(request: Request): Result<T> {
        return withIO {
            val resp = client.newCall(request).execute()
            if (resp.isSuccessful) {
                val body = resp.body?.string()
                Logger.i("body:${body}")
                if (body.isNullOrEmpty()) {
                    Result.Error("body is null")
                } else {
                    val respData = when (T::class) {
                        String::class -> body as T
                        else -> MoshiUtils.fromJson<T>(body)!!
                    }

                    Logger.i("respData: $respData")
                    Result.Success(respData)
                }
            } else {
                Result.Error(resp.message)
            }
        }

    }


    // Result 类
    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val errMsg: String) : Result<Nothing>()
    }

}