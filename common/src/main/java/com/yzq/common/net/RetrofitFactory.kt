package com.yzq.common.net

import android.util.Log
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.yzq.common.BuildConfig
import com.yzq.common.net.constants.ServerConstants
import com.yzq.lib_base.utils.MoshiUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    /*线程安全的懒汉式单例*/
    companion object {
        val instance: RetrofitFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitFactory()
        }

    }

    private fun initOkhttpClient(): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
//            .addInterceptor(RequestHeadersInterceptor())
//            .addInterceptor(RequestEncryptInterceptor())
            .addInterceptor(initLogInterceptor())

        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(OkHttpProfilerInterceptor())
        }
//            .addInterceptor(ResponseDecryptInterceptor())


        return okHttpBuilder.build()
    }


    /*
    * 日志拦截器
    * */
    private fun initLogInterceptor(): HttpLoggingInterceptor {

        val interceptor = HttpLoggingInterceptor { message ->
            if (BuildConfig.DEBUG) {
                Log.i("Retrofit", message)
            }
        }

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }

    /*
    * 具体服务实例化
    * */
    fun <T> getService(service: Class<T>): T {

        return retrofit.create(service)
    }
}



