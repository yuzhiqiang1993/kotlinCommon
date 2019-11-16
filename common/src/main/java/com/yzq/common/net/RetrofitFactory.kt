package com.yzq.common.net

import android.util.Log
import com.google.gson.Gson
import com.yzq.common.BuildConfig
import com.yzq.common.constants.ServerConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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

        val gson = Gson().newBuilder()
            .setLenient()
            .serializeNulls()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(ServerConstants.apiUrl)
            .client(initOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
//            .addInterceptor(ResponseDecryptInterceptor())

        return okHttpBuilder.build()
    }


    /*
    * 日志拦截器
    * */
    private fun initLogInterceptor(): HttpLoggingInterceptor {

        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {

                if (BuildConfig.DEBUG) {
                    Log.i("Retrofit", message)
                }
            }
        })

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



