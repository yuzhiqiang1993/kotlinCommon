package com.yzq.common.net


import com.yzq.base.utils.MoshiUtils
import com.yzq.coroutine.safety_coroutine.withIO
import com.yzq.coroutine.thread_pool.ThreadPoolManager
import com.yzq.logger.Logger
import okhttp3.Dispatcher
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit


/**
 * @description 封装的网络请求工具类
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

object OkHttpUtils {

    val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(LoggingInterceptor())
        .dispatcher(Dispatcher(ThreadPoolManager.instance.ioThreadPoolExecutor))//设置线程池,okhttp本身做了限制，默认同时最多支持64个请求
        .build()


    private val downloadClient =
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
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
        val httpUrl = url.toHttpUrlWithParams(params)
        val request = Request.Builder().url(httpUrl).build()
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
        val requestBody = params.toJsonRequestBody()
        val request = Request.Builder().url(url).post(requestBody).build()
        return request<T>(request)
    }


    suspend inline fun <reified T> request(request: Request): Result<T> {
        return withIO {
            val resp = client.newCall(request).execute()
            if (resp.isSuccessful) {
                val body = resp.body?.string()
                Logger.i("body:${body}")
                if (body.isNullOrEmpty()) {
                    Result.Error("Response body is null")
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


    suspend fun downloadFile(
        url: String, destFile: File, progressCallback: (progress: Int, total: Long) -> Unit
    ): Result<File> {
        return withIO {
            val request = Request.Builder().url(url).build()
            downloadClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withIO Result.Error(response.message)
                }
                val body = response.body ?: return@withIO Result.Error("Response body is null")
                val contentLength = body.contentLength()
                Logger.i("文件大小：${contentLength}")
                body.byteStream().use { input ->
                    FileOutputStream(destFile).use { output ->
                        val buffer = ByteArray(4 * 1024) // buffer size
                        var bytesDownloaded = 0L
                        var read: Int
                        var lastCallbackTime = System.currentTimeMillis()
                        var lastProgress = 0

                        while (input.read(buffer).also { read = it } != -1) {
                            output.write(buffer, 0, read)
                            bytesDownloaded += read
                            val progress = (100 * bytesDownloaded / contentLength)
                            if (System.currentTimeMillis() - lastCallbackTime > 200 && progress.toInt() != lastProgress) {
                                lastProgress = progress.toInt()
                                lastCallbackTime = System.currentTimeMillis()
                                progressCallback(lastProgress, contentLength)
                            }
                        }/*保证100一定会被回调一次*/
                        if (lastProgress != 100) {
                            lastProgress = 100
                            progressCallback.invoke(lastProgress, contentLength)
                        }
                    }
                }
                Result.Success(destFile)
            }
        }

    }

    fun Map<String, String>.toJsonRequestBody(): RequestBody {
        val jsonStr = MoshiUtils.toJson(this)
        return jsonStr.toRequestBody("application/json".toMediaType())
    }

    fun String.toHttpUrlWithParams(params: Map<String, String>): HttpUrl {
        val builder = this.toHttpUrl().newBuilder()
        params.forEach { (key, value) -> builder.addQueryParameter(key, value) }
        return builder.build()
    }


    // Result 类
    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val errMsg: String) : Result<Nothing>()
    }

}