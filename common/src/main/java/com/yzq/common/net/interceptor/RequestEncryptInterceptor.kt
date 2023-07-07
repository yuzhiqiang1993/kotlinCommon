package com.yzq.common.net.interceptor


import com.yzq.common.net.constants.ServerConstants
import com.yzq.common.net.utils.AESUtil
import com.yzq.common.net.utils.RSAUtil
import com.yzq.logger.LogCat
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.Buffer
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.Locale

/**
 * @description: 对请求数据进行加密处理
 * @author : yzq
 * @date   : 2019/3/16
 * @time   : 16:37
 *
 */

class RequestEncryptInterceptor : Interceptor {
    private var defaultCharset: Charset = Charset.forName("UTF-8")

    override fun intercept(chain: Interceptor.Chain): Response {
        LogCat.i("开始处理提交给服务端的数据----------------------")
        var request = chain.request()

        val url = request.url
        val hostPath = "${url.scheme}://${url.host}".trim()
        LogCat.i("本次请求的主机地址:$hostPath")
        LogCat.i("ServerConstants:${ServerConstants.serverUrl}")
        if (!ServerConstants.serverUrl.startsWith(hostPath)) {
            return chain.proceed(request)
        }


        val method = request.method.trim().lowercase(Locale.ROOT)
        LogCat.i("请求方式：${method}")

        if (method == "get" || method == "delete") {
            runCatching {
                encryptUrlParam(request)
            }.onSuccess {
                request = it
            }.onFailure {
                it.printStackTrace()
                LogCat.i("url参数加密异常")
            }

        } else if (method == "post" || method == "put") {
            runCatching {
                encryptRequestBody(request)
            }.onSuccess {
                request = it
            }.onFailure {
                it.printStackTrace()
                LogCat.i("请求体加密异常")
            }
        }

        return chain.proceed(request)


    }


    /**
     * Encrypt request body
     *请求体加密
     * @param request
     * @return
     */
    private fun encryptRequestBody(request: Request): Request {
        val requestBody = request.body ?: return request
        var requestCharset = defaultCharset
        val contentType = requestBody.contentType()
        if (contentType != null) {
            requestCharset = contentType.charset(defaultCharset)!!
            LogCat.i("contentType===>${contentType}")
            LogCat.i("contentType===> type:${contentType.type},subType:${contentType.subtype}")

            /*如果是二进制上传  则不进行加密*/
            if (contentType.type.lowercase(Locale.ROOT) == "multipart") {
                LogCat.i("上传文件，不加密")
                return request
            }
        }
        /*获取请求的数据*/
        val buffer = Buffer()
        requestBody.writeTo(buffer)

        val requestData =
            URLDecoder.decode(buffer.readString(requestCharset).trim(), "utf-8")
        LogCat.i("请求的数据为：${requestData}")

        /*产生一个随机数*/
        val randomKey = AESUtil.getRandomKey(16)
        LogCat.i("生成的随机数：${randomKey}")

        /*使用产生的随机数对请求的数据进行加密*/
        val aesEncryptData = AESUtil.encrypt(requestData, randomKey)

        LogCat.i("加密后的请求数据为：${aesEncryptData}")

        /*再使用公钥对随机的key进行加密 得到加密后的key*/
        val encryptAesKeyStr = RSAUtil.encryptByPublic(
            randomKey,
            ServerConstants.RSA_PUB_KEY
        )

        /*将加密后的key放到header里*/
        LogCat.i("加密后的key：${encryptAesKeyStr}")


        /*然后将使用Aes加密过后的数据放到request里*/
        val newRequestBody = aesEncryptData.toRequestBody(contentType)

        /*将加密过后的AES随机key放到请求头中并构建新的request*/
        val newRequestBuilder = request.newBuilder()
        newRequestBuilder.addHeader(
            ServerConstants.AES_KEY,
            encryptAesKeyStr
        )
        val method = request.method.trim().lowercase(Locale.ROOT)
        when (method) {
            "post" -> newRequestBuilder.post(newRequestBody)
            "put" -> newRequestBuilder.put(newRequestBody)
        }

        return newRequestBuilder.build()
    }


    /**
     * Encrypt url param
     * url参数加密
     *
     * @param request
     * @return
     */
    private fun encryptUrlParam(request: Request): Request {

        /*如果是null 不加密直接返回*/
        val httpUrl = request.url
        httpUrl.encodedQuery ?: return request

        val apiPath =
            "${httpUrl.scheme}://${httpUrl.host}:${httpUrl.port}${httpUrl.encodedPath}".trim()
        LogCat.i("原本请求的接口地址：$apiPath")
        val encodedQuery = httpUrl.encodedQuery
        LogCat.i("请求参数 encodedQuery:" + encodedQuery!!)
        /*先获取随机Key*/
        val randomKey = AESUtil.getRandomKey(16)
        LogCat.i("radomKey：$randomKey")
        /*AES加密后的请求数据*/
        val encryptQuery = AESUtil.encrypt(encodedQuery, randomKey)
        LogCat.i("加密后的参数：$encryptQuery")
        /*用RSA公钥对随机key进行加密*/
        val encryptRandomKey = RSAUtil.encryptByPublic(
            randomKey,
            ServerConstants.RSA_PUB_KEY
        )
        LogCat.i("RSA公钥加密后的随机key：$encryptRandomKey")
        val newGet = "$apiPath?param=$encryptQuery"

        /*返回新的加密后的request*/
        return request.newBuilder().url(newGet)
            .addHeader(
                ServerConstants.AES_KEY,
                encryptRandomKey
            )
            .build()
    }


}