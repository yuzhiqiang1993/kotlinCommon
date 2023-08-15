package com.yzq.common.net

import com.yzq.common.net.constants.ServerConstants
import me.jessyan.progressmanager.ProgressManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


/**
 * @description: FileRetrofitFactory
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 16:44
 *
 */

class FileRetrofitFactory private constructor() {

    private val retrofit: Retrofit


    init {
        retrofit = Retrofit.Builder()
            .baseUrl(ServerConstants.apiUrl)
            .client(initOkhttpClient())
            .build()

    }


    /*线程安全的懒汉式单例*/
    companion object {
        val instance: FileRetrofitFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            FileRetrofitFactory()
        }

    }

    private fun initOkhttpClient(): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        return ProgressManager.getInstance().with(okHttpBuilder).build()
    }


    /*
    * 具体服务实例化
    * */
    fun <T> getService(service: Class<T>): T {

        return retrofit.create(service)
    }
}



