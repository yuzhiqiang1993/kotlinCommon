package com.yzq.kotlincommon

import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.yzq.common.constants.StoragePath
import com.yzq.kotlincommon.ui.activity.MainActivity
import com.yzq.lib_base.BaseApp


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
        initBugly()

        StoragePath.logPathInfo()


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


}