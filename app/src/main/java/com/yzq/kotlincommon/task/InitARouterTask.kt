package com.yzq.kotlincommon.task

import com.aice.appstartfaster.task.AppStartTask
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.yzq.lib_base.BaseApp
import com.yzq.lib_base.BuildConfig
import java.util.*

/**
 * @description: 初始化ARouter
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/11/29
 * @time   : 8:12 下午
 */

class InitARouterTask : AppStartTask() {
    override fun run() {
        LogUtils.i("开始执行：${Thread.currentThread().name}")
        if (BuildConfig.DEBUG) {
            ARouter.openLog() // 开启日志
            ARouter.openDebug() // 使用InstantRun的时候，需要打开该开关，上线之后关闭，否则有安全风险
            ARouter.printStackTrace() // 打印日志的时候打印线程堆栈
        }

        ARouter.init(BaseApp.INSTANCE)

    }

    override fun needWait() = true

    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {
        val dependsTaskList: MutableList<Class<out AppStartTask?>> = ArrayList()
        dependsTaskList.add(InitUtilsTask::class.java)

        return dependsTaskList
    }

    override fun isRunOnMainThread() = false
}