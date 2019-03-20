package com.yzq.kotlincommon

import com.taobao.sophix.SophixManager
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.yzq.common.BaseApp
import com.yzq.kotlincommon.ui.MainActivity


/**
 * @description: Application基类
 * @author : yzq
 * @date   : 2019/3/18
 * @time   : 11:28
 *
 */

class App : BaseApp() {


    override fun onCreate() {
        super.onCreate()
        SophixManager.getInstance().queryAndLoadNewPatch();
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
        Bugly.init(this, "52e655831e", BuildConfig.DEBUG)

    }

//    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(base)
//        MultiDex.install(this)
//
//    }

}