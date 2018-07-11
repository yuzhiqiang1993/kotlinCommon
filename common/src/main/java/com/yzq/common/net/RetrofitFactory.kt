package com.yzq.common.net

import android.util.Log
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.BaseApp
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
 * @author : yzq
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

    //初始化
    init {

        interceptor = Interceptor {

            var request = it.request()
                    .newBuilder()
                    .addHeader(ServerConstants.DEVICE_ID, DeviceUtils.getAndroidID())
                    .build()

            it.proceed(request)


        }

        retrofit = Retrofit.Builder()
                .baseUrl(ServerConstants.BASE_URL)
                .client(initOkhttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()


    }

    private fun initOkhttpClient(): OkHttpClient? {


        var okHttpBuilder = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(interceptor)



        if (BuildConfig.DEBUG) {
            LogUtils.i("BuildConfig.DEBUG is ${BuildConfig.DEBUG}")
            okHttpBuilder.addInterceptor(initLogInterceptor())
        }




        return ProgressManager.getInstance().with(okHttpBuilder).build()
    }

    /*
        日志拦截器
     */
    private fun initLogInterceptor(): HttpLoggingInterceptor {

        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {

            Log.i("Retrofit",it)
        })




        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor


    }


    /*
        具体服务实例化
     */
    fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }
}



