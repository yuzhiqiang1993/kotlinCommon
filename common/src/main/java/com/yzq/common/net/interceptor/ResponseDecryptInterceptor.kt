package com.yzq.common.net.interceptor

import android.text.TextUtils
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.ServerConstants
import com.yzq.common.utils.AESUtils
import com.yzq.common.utils.Base64
import com.yzq.common.utils.RSAUtils
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
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


        LogUtils.i("ResponseDecryptInterceptor")

        val request = chain.request()
        val mediaType = MediaType.parse("text/plain; charset=utf-8")
        val response = chain.proceed(request)

        //LogUtils.i("响应是否成功:${response.isSuccessful}")
        if (response.isSuccessful) {

            val responseBody = response.body()

            /*获取请求头中的key*/
            val aesKey = response.header(ServerConstants.AES_KEY)

            if (!TextUtils.isEmpty(aesKey) && responseBody != null) {
                /*开始解密*/
                try {


                    /*1.先用公钥解密获取AES密码  成功即可验证服务端的身份*/
                    val decryptAesKey = RSAUtils.decryptByPublicKey(Base64.decode(aesKey), ServerConstants.RSA_PUB_KEY)
                    /*2.使用aesKey对密文进行解密获取最终的明文*/

                    val source = responseBody.source()
                    source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                    val buffer = source.buffer()
                    var charset = Charset.forName("UTF-8")

                    val contentType = responseBody.contentType()
                    if (contentType != null) {
                        charset = contentType.charset(charset)
                    }

                    val bodyString = buffer.clone().readString(charset).trim()

                    LogUtils.i("响应的源数据为:${bodyString}")

                    val responseData = AESUtils.decrypt(bodyString, String(decryptAesKey))
                    LogUtils.i("解密后的明文：${responseData}")

                    /*将解密后的明文返回*/
                    val newResponseBody = ResponseBody.create(mediaType, responseData)

                    response.newBuilder().body(newResponseBody).build()

                } catch (e: Exception) {

                    /*异常说明解密失败 信息被篡改 直接返回即可 */

                    println("解密出现异常：" + e)
                    LogUtils.i("解密失败  直接返回")

                }

            }


        }


        return response


    }


}