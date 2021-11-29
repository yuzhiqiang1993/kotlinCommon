package com.yzq.kotlincommon.task

import com.aice.appstartfaster.task.AppStartTask
import com.blankj.utilcode.util.LogUtils
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.yzq.kotlincommon.ui.activity.MainActivity
import com.yzq.lib_base.AppContext
import java.util.*

/**
 * @description: 初始化bugly
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/11/29
 * @time   : 8:11 下午
 */

class InitBuglyTask : AppStartTask() {
    override fun run() {
        LogUtils.i("开始执行：${Thread.currentThread().name}")

        /*初始化Bugly*/
        Beta.showInterruptedStrategy = true
        Beta.canShowUpgradeActs.add(MainActivity::class.java)
        /*第三个参数表示是否在debug下也上报日志信息*/
        Bugly.init(AppContext, "52e655831e", false)
    }

    override fun needWait() = true

    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {
        val dependsTaskList: MutableList<Class<out AppStartTask?>> = ArrayList()
        dependsTaskList.add(InitUtilsTask::class.java)

        return dependsTaskList
    }

    override fun isRunOnMainThread() = false
}