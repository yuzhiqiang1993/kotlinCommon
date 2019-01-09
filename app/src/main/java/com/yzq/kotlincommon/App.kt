package com.yzq.kotlincommon

import android.content.Context
import androidx.multidex.MultiDex
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