package com.yzq.common.net.interceptor

import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.ServerConstants
import com.yzq.common.utils.AESUtils
import com.yzq.common.utils.Base64
import com.yzq.common.utils.RSAUtils
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset


/**
 * @description: 对请求数据进行加密处理
 * @author : yzq
 * @date   : 2019/3/16
 * @time   : 16:37
 *
 */

class RequestEncryptInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIarYvrIMZGHKa8f2E6ubg0//28R1zJ4ArD+XELXYvDrM8UBR42PqJCpjPN3hC91YAnnk2Y9U+X5o/rGxH5ZTZzYy+rkAmZFJa1fK2mWDxPYJoxH+DGHQc+h8t83BMB4pKqVPhcJVF6Ie+qpD5RFUU/e5iEz8ZZFDroVE3ubKaKwIDAQAB"

        var request = chain.request()

        val requestBody = request.body()

        /*判断请求体是否为空  不为空则执行以下操作*/
        if (requestBody != null) {

            /*获取请求的数据*/
            val buffer = Buffer()
            var charset = Charset.forName("UTF-8")

            requestBody.writeTo(buffer)
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType!!.charset(charset)
            }
            val requestData = buffer.readString(charset)

            LogUtils.i("请求的数据为：${requestData}")

            /*产生一个随机数*/
            val randomKey = AESUtils.getRandomKey(16)
            LogUtils.i("生成的随机数：${randomKey}")

            /*使用产生的随机数对请求的数据进行加密*/
            val aesEncryptData = AESUtils.encrypt(requestData, randomKey)

            LogUtils.i("加密后的请求数据为：${aesEncryptData}")

            /*再使用公钥对随机的key进行加密 得到加密后的key*/
            val encryptAesKeyBytes = RSAUtils.encryptByPublicKey(randomKey.toByteArray(), publicKey)

            /*将加密后的key放到header里*/
            val encryptAesKeyStr = Base64.encode(encryptAesKeyBytes)
            LogUtils.i("加密后的key：${encryptAesKeyStr}")


            /*然后将使用Aes加密过后的数据放到request里*/
            val newRequestBody = RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), aesEncryptData)
            request = request.newBuilder()
                    .addHeader(ServerConstants.AES_KEY, Base64.encode(encryptAesKeyBytes))
                    .post(newRequestBody)
                    .build()

        }


        return chain.proceed(request)


    }


}