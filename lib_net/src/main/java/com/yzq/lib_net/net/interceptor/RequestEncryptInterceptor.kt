package com.yzq.lib_net.net.interceptor


import com.blankj.utilcode.util.LogUtils
import com.yzq.lib_net.constants.ServerConstants
import com.yzq.lib_net.utils.AESUtil
import com.yzq.lib_net.utils.RSAUtil
import okhttp3.Interceptor
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.Buffer
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.*

/**
 * @description: 对请求数据进行加密处理
 * @author : yzq
 * @date   : 2019/3/16
 * @time   : 16:37
 *
 */

class RequestEncryptInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        LogUtils.i("开始处理提交给服务端的数据----------------------")

        var request = chain.request()

        var charset = Charset.forName("UTF-8")

        val method = request.method.trim().toLowerCase(Locale.ROOT)

        LogUtils.i("请求方式：${method}")


        val url = request.url


        val apiPath = "${url.scheme}://${url.host}:${url.port}${url.encodedPath}".trim()

        val hostPath = "${url.scheme}://${url.host}".trim()
        LogUtils.i("本次请求的主机地址:$hostPath")
        LogUtils.i("ServerConstants:${ServerConstants.serverUrl}")


        if (!ServerConstants.serverUrl.startsWith(hostPath)) {
            return chain.proceed(request)
        }


        if (method.equals("get") || method.equals("delete")) {

            if (url.encodedQuery != null) {
                try {
                    val queryparamNames = request.url.encodedQuery

                    LogUtils.i("queryparamNames:" + queryparamNames!!)


                    /*先获取随机Key*/
                    val randomKey = AESUtil.getRandomKey(16)
                    LogUtils.i("radomKey：$randomKey")

                    /*AES加密后的请求数据*/
                    val encryptqueryparamNames = AESUtil.encrypt(queryparamNames, randomKey)
                    LogUtils.i("加密后的参数：$encryptqueryparamNames")

                    /*用RSA公钥对随机key进行加密*/
                    val encryptRandomKey = RSAUtil.encryptByPublic(
                        randomKey,
                        ServerConstants.RSA_PUB_KEY
                    )

                    LogUtils.i("RSA公钥加密后的随机key：$encryptRandomKey")


                    val newGet = "$apiPath?param=$encryptqueryparamNames"

                    request = request.newBuilder().url(newGet)
                        .addHeader(
                            ServerConstants.AES_KEY,
                            encryptRandomKey
                        )
                        .build()

                } catch (e: Exception) {
                    e.printStackTrace()

                    LogUtils.i("Get加密异常")

                    return chain.proceed(request)
                }

            }

        } else {

            val requestBody = request.body

            /*判断请求体是否为空  不为空则执行以下操作*/
            if (requestBody != null) {

                val contentType = requestBody.contentType()



                if (contentType != null) {


                    charset = contentType.charset(charset)!!

                    LogUtils.i("contentType===>${contentType}")
                    LogUtils.i("contentType===> type:${contentType.type},subType:${contentType.subtype}")

                    /*如果是二进制上传  则不进行加密*/
                    if (contentType.type.toLowerCase(Locale.ROOT).equals("multipart")) {
                        LogUtils.i("上传文件，不加密")
                        return chain.proceed(request)
                    }

                }


                /*获取请求的数据*/
                try {
                    val buffer = Buffer()
                    requestBody.writeTo(buffer)

                    val requestData = URLDecoder.decode(buffer.readString(charset).trim(), "utf-8")

                    LogUtils.i("请求的数据为：${requestData}")

                    /*产生一个随机数*/
                    val randomKey = AESUtil.getRandomKey(16)
                    LogUtils.i("生成的随机数：${randomKey}")

                    /*使用产生的随机数对请求的数据进行加密*/
                    val aesEncryptData = AESUtil.encrypt(requestData, randomKey)

                    LogUtils.i("加密后的请求数据为：${aesEncryptData}")

                    /*再使用公钥对随机的key进行加密 得到加密后的key*/
                    val encryptAesKeyStr = RSAUtil.encryptByPublic(
                        randomKey,
                        ServerConstants.RSA_PUB_KEY
                    )

                    /*将加密后的key放到header里*/
                    LogUtils.i("加密后的key：${encryptAesKeyStr}")


                    /*然后将使用Aes加密过后的数据放到request里*/
                    val newRequestBody = aesEncryptData.toRequestBody(contentType)

                    /*将加密过后的AES随机key放到请求头中并构建新的request*/
                    val newRequestBuilder = request.newBuilder()
                    newRequestBuilder.addHeader(
                        ServerConstants.AES_KEY,
                        encryptAesKeyStr
                    )

                    when (method) {
                        "post" -> newRequestBuilder.post(newRequestBody)
                        "put" -> newRequestBuilder.put(newRequestBody)
                    }

                    request = newRequestBuilder.build()

                } catch (e: Exception) {
                    LogUtils.e("加密异常====》${e}")
                    return chain.proceed(request)
                }
            }
        }

        return chain.proceed(request)


    }


}