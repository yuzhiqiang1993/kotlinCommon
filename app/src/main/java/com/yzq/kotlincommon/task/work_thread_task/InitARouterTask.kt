package com.yzq.kotlincommon.task.work_thread_task

import com.aice.appstartfaster.task.AppStartTask
import com.alibaba.android.arouter.launcher.ARouter
import com.yzq.application.BaseApp
import com.yzq.base.BuildConfig
import com.yzq.base.startup.base.WorkThreadTask

/**
 * @description: 初始化ARouter
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/11/29
 * @time   : 8:12 下午
 */

class InitARouterTask : WorkThreadTask() {

    override fun taskRun() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog() // 开启日志
            ARouter.openDebug() // 使用InstantRun的时候，需要打开该开关，上线之后关闭，否则有安全风险
            ARouter.printStackTrace() // 打印日志的时候打印线程堆栈
        }
        ARouter.init(BaseApp.getInstance())
    }

    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {
        val dependsTaskList: MutableList<Class<out AppStartTask?>> = ArrayList()
        dependsTaskList.add(InitUtilsTask::class.java)

        return dependsTaskList
    }

}