package com.yzq.kotlincommon

import android.content.Context
import androidx.multidex.MultiDex
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.yzq.lib_base.BaseApp
import com.yzq.kotlincommon.ui.activity.MainActivity


/**
 * @description: Application基类
 * @author : yzq
 * @date   : 2019/3/18
 * @time   : 11:28
 *
 */

class App : com.yzq.lib_base.BaseApp() {


    override fun onCreate() {
        super.onCreate()
        // SophixManager.getInstance().queryAndLoadNewPatch();
        initBugly()

    }


    /**
     * 初始化bugly
     *
     */
    fun initBugly() {
        /*初始化Bugly*/
        Beta.showInterruptedStrategy = true
        Beta.canShowUpgradeActs.add(MainActivity::class.java)
        /*第三个参数表示是否在debug下也上报日志信息*/
        Bugly.init(this, "52e655831e", false)

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)

    }

}