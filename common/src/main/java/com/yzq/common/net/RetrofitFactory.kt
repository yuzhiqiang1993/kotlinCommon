package com.yzq.common.net

import android.util.Log
import com.blankj.utilcode.util.DeviceUtils
import com.yzq.common.BuildConfig
import com.yzq.common.constants.ServerConstants
import me.jessyan.progressmanager.ProgressManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @description: RetrofitFactory
 * @author : yzq *
 * @date   : 2018/7/9
 * @time   : 16:44
 *
 */

class RetrofitFactory private constructor() {

    private val retrofit: Retrofit

    companion object {
        val instance: RetrofitFactory by lazy {
            RetrofitFactory()
        }
    }

    private var interceptor: Interceptor


    init {
        interceptor = Interceptor {

            val request = it.request().newBuilder()
                    .header("Cache-Control", "no-store")//不读取缓存
                    .addHeader(ServerConstants.DEVICE_ID, DeviceUtils.getAndroidID())
                    .build()

            it.proceed(request)
        }

        retrofit = Retrofit.Builder()
                .baseUrl(ServerConstants.getApiUrl())
                .client(initOkhttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()


    }


    private fun initOkhttpClient(): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(initLogInterceptor())

        return ProgressManager.getInstance().with(okHttpBuilder).build()
    }


    /*
    * 日志拦截器
    * */
    private fun initLogInterceptor(): HttpLoggingInterceptor {

        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {

            if (BuildConfig.DEBUG) {
                Log.i("Retrofit", it)
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



