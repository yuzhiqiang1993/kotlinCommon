package com.yzq.lib_net.net.interceptor


import com.blankj.utilcode.util.LogUtils
import com.yzq.lib_net.constants.ServerConstants
import com.yzq.lib_net.utils.AESUtil
import com.yzq.lib_net.utils.RSAUtil
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.nio.charset.Charset

/**
 * @description: 对响应数据进行解密
 * @author : yzq
 * @date   : 2019/3/20
 * @time   : 16:13
 *
 */

class ResponseDecryptInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {

        LogUtils.i("开始处理服务端响应的数据----------------------")


        val request = chain.request()
        // val mediaType = MediaType.parse("application/json; charset=utf-8")
        var response = chain.proceed(request)


        if (response.isSuccessful) {

            val responseBody = response.body

            /*获取请求头中的key*/
            val aesKey = response.header(ServerConstants.AES_KEY)

            LogUtils.i("响应头中的AESKey:${aesKey}")

            if (aesKey != null && responseBody != null) {
                /*开始解密*/
                try {

                    /*1.先用RSA公钥对随机Key进行解密*/
                    val decryptAesKey = RSAUtil.decryptByPublic(aesKey, ServerConstants.RSA_PUB_KEY)

                    LogUtils.i("decryptAesKey:${decryptAesKey}")
                    /*2.使用aesKey对密文进行解密获取最终的明文*/

                    val source = responseBody.source()
                    source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                    val buffer = source.buffer
                    var charset = Charset.forName("UTF-8")

                    val contentType = responseBody.contentType()
                    if (contentType != null) {
                        charset = contentType.charset(charset)!!
                    }

                    val bodyString = buffer.clone().readString(charset).trim()

                    LogUtils.i("响应的原数据为:${bodyString}")

                    val responseData = AESUtil.decrypt(bodyString, decryptAesKey)
                    LogUtils.i("解密后的明文：${responseData}")

                    /*将解密后的明文返回*/
                    val newResponseBody = responseData.trim().toResponseBody(contentType)
                    response = response.newBuilder().body(newResponseBody).build()

                } catch (e: Exception) {
                    /*异常说明解密失败 信息被篡改 直接返回即可 */
                    LogUtils.e("解密异常====》${e}")
                    return response
                }

            } else {
                LogUtils.i("响应体为空")
            }

        }
        return response

    }


}