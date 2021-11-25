package com.yzq.kotlincommon

import android.content.Context
import android.os.Trace
import com.blankj.utilcode.util.LogUtils
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.yzq.common.constants.StoragePath
import com.yzq.kotlincommon.ui.activity.MainActivity
import com.yzq.lib_base.BaseApp
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

/**
 * @description: Application基类
 * @author : yzq
 * @date   : 2019/3/18
 * @time   : 11:28
 *
 */

class App : BaseApp() {

    override fun onCreate() {
        LogUtils.i("onCreate")
        Trace.beginSection("BaseAppInit")
        super.onCreate()

        Trace.beginSection("initBugly")
        initBugly()
        Trace.endSection()

        StoragePath.logPathInfo()
        Trace.endSection()

    }

    /**
     * 初始化bugly
     *
     */
    private fun initBugly() {
        /*初始化Bugly*/
        Beta.showInterruptedStrategy = true
        Beta.canShowUpgradeActs.add(MainActivity::class.java)
        /*第三个参数表示是否在debug下也上报日志信息*/
        Bugly.init(this, "52e655831e", false)

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }

}