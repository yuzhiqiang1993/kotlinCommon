package com.yzq.common.net

import com.yzq.base.utils.MoshiUtils
import com.yzq.common.net.constants.ServerConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @description: RetrofitFactory
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 16:44
 *
 */

class RetrofitFactory private constructor() {

    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(ServerConstants.apiUrl)
            .client(initOkhttpClient())
            .addConverterFactory(MoshiConverterFactory.create(MoshiUtils.moshi))
            .build()

    }

    //线程安全的懒汉式单例
    companion object {
        val instance: RetrofitFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitFactory()
        }

    }

    private fun initOkhttpClient(): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder()
            .callTimeout(5, TimeUnit.SECONDS)
//            .addInterceptor(RequestHeadersInterceptor())
//            .addInterceptor(RequestEncryptInterceptor())
            .addInterceptor(LoggingInterceptor())


//            .addInterceptor(ResponseDecryptInterceptor())


        return okHttpBuilder.build()
    }


    /*
    * 具体服务实例化
    * */
    fun <T> getService(service: Class<T>): T {

        return retrofit.create(service)
    }
}



