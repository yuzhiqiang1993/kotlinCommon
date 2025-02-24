package com.yzq.net

import com.yzq.coroutine.thread_pool.ThreadPoolManager
import com.yzq.net.constants.ServerConstants
import com.yzq.net.interceptor.LoggingInterceptor
import com.yzq.net.interceptor.TrafficInterceptor
import com.yzq.util.MoshiUtils
import okhttp3.Dispatcher
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
            .addInterceptor(TrafficInterceptor())
            .dispatcher(Dispatcher(ThreadPoolManager.instance.ioThreadPoolExecutor))//设置线程池,okhttp本身做了限制，默认同时最多支持64个请求


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



