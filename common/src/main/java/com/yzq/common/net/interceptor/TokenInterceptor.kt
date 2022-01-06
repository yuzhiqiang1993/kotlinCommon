package com.yzq.common.net.interceptor

import com.blankj.utilcode.util.LogUtils
import com.yzq.common.utils.MMKVUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @description: Token 拦截器
 * @author : XeonYu
 * @date   : 2020/9/9
 * @time   : 15:40
 */

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        /*获得resp*/
        val resp = chain.proceed(request)
        /*判断token是否过期*/
        if (resp.code == 401) {

            try {

                LogUtils.i("过期之前的idToken:${MMKVUtil.token}")

                /*token过期  刷新token*/
                //refreshToken()
                /*构建新的请求*/
                val newRequestBuilder = request.newBuilder()

                LogUtils.i("刷新之后的idToken:${MMKVUtil.token}")

                newRequestBuilder.header("Authorization", "Bearer ${MMKVUtil.token}")

                return chain.proceed(newRequestBuilder.build())
            } catch (e: Exception) {
                LogUtils.e("TokenInterceptor error")
                e.printStackTrace()
            }
            finally {

            }

        }
        return resp
    }
}