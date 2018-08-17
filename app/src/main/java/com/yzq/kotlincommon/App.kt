package com.yzq.kotlincommon

import android.content.Context
import android.support.multidex.MultiDex
import com.yzq.common.BaseApp

class App : BaseApp() {


    override fun onCreate() {
        super.onCreate()
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}