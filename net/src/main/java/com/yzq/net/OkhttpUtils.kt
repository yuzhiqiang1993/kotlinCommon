package com.yzq.net


import com.yzq.coroutine.ext.withIO
import com.yzq.coroutine.thread_pool.ThreadPoolManager
import com.yzq.logger.Logger
import com.yzq.net.core.ApiResult
import com.yzq.net.interceptor.LoggingInterceptor
import com.yzq.net.interceptor.TrafficInterceptor
import com.yzq.util.MoshiUtils
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
import java.io.InputStream
import java.util.concurrent.TimeUnit


/**
 * @description 封装的网络请求工具类
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

object OkHttpUtils {

    val client =
        OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(LoggingInterceptor())
            .addInterceptor(TrafficInterceptor())
            .dispatcher(Dispatcher(ThreadPoolManager.instance.ioThreadPoolExecutor))//设置线程池,okhttp本身做了限制，默认同时最多支持64个请求
            .build()


//    private val downloadClient =
//        OkHttpClient.Builder()
//            .connectTimeout(5, TimeUnit.SECONDS)
//            .readTimeout(5, TimeUnit.SECONDS)
//            .build()


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
        headers: Map<String, String> = emptyMap()
    ): ApiResult<T> = runCatching {
        val httpUrl = url.toHttpUrlWithParams(params)
        val requestBuilder = Request.Builder().url(httpUrl)

        if (headers.isNotEmpty()) {
            headers.forEach { requestBuilder.addHeader(it.key, it.value) }
        }
        return request<T>(requestBuilder.build())
    }.getOrElse {
        ApiResult.Exception(it)
    }

    /**
     * form 表单的请求方式
     */
    suspend inline fun <reified T> postForm(
        url: String,
        formParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): ApiResult<T> = runCatching {
        val builder = url.toHttpUrl().newBuilder()
        val formBodyBuilder = okhttp3.FormBody.Builder()
        formParams.forEach { formBodyBuilder.add(it.key, it.value) }
        val requestBody = formBodyBuilder.build()

        val requestBuilder = Request.Builder().url(builder.build()).post(requestBody)

        if (headers.isNotEmpty()) {
            headers.forEach { requestBuilder.addHeader(it.key, it.value) }
        }

        val result = request<T>(requestBuilder.build())
        return result
    }.getOrElse {
        ApiResult.Exception(it)
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
        headers: Map<String, String> = emptyMap()
    ): ApiResult<T> = kotlin.runCatching {
        val requestBody = params.toJsonRequestBody()

        val requestBuilder = Request.Builder().url(url).post(requestBody)

        if (headers.isNotEmpty()) {
            headers.forEach { requestBuilder.addHeader(it.key, it.value) }
        }
        request<T>(requestBuilder.build())
    }.getOrElse {
        ApiResult.Exception(it)
    }


    suspend inline fun <reified T> request(request: Request): ApiResult<T> {
        return withIO {
            try {
                val response = client.newCall(request).execute()

                if (!response.isSuccessful) {
                    return@withIO ApiResult.Error(response.code, response.message)
                }
                val body = response.body ?: return@withIO ApiResult.Error(
                    response.code, "Response body is null"
                )
                return@withIO when (T::class) {
                    InputStream::class -> {
                        ApiResult.Success(body.byteStream() as T)
                    }

                    String::class -> {
                        ApiResult.Success(body.string() as T)
                    }

                    else -> {
                        ApiResult.Success(
                            MoshiUtils.fromJson<T>(body.string())!!
                        )
                    }
                }
            } catch (e: Exception) {
                ApiResult.Exception(e)
            }

        }

    }


    suspend fun downloadFile(
        url: String, destFile: File, progressCallback: (progress: Int, total: Long) -> Unit
    ): ApiResult<File> {
        return withIO {
            val request = Request.Builder().url(url).build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withIO ApiResult.Error(response.code, response.message)
                }
                val body = response.body ?: return@withIO ApiResult.Error(
                    response.code, "Response body is null"
                )
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
                        }
                        //保证100一定会被回调一次
                        if (lastProgress != 100) {
                            lastProgress = 100
                            progressCallback.invoke(lastProgress, contentLength)
                        }
                    }
                }
                ApiResult.Success(destFile)
            }
        }

    }

    fun Map<String, String>.toJsonRequestBody(): RequestBody {
        val jsonStr = MoshiUtils.toJson(this)

        if (jsonStr.isNullOrEmpty()) {
            throw IllegalArgumentException("请求参数解析异常")
        }

        return jsonStr.toRequestBody("application/json".toMediaType())
    }

    fun String.toHttpUrlWithParams(params: Map<String, String>): HttpUrl {
        val builder = this.toHttpUrl().newBuilder()
        params.forEach { (key, value) -> builder.addQueryParameter(key, value) }
        return builder.build()
    }

}