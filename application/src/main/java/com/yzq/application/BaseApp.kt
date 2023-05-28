package com.yzq.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

/**
 * @description Application基类
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/9/30
 * @time 11:02
 */

open class BaseApp : Application() {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    companion object {
        const val TAG = "BaseApp"
        private lateinit var instance: BaseApp
        fun getInstance(): BaseApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppManager.init(this)
    }

}
