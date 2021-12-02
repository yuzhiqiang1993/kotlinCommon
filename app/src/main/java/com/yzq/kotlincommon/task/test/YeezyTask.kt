package com.yzq.kotlincommon.task.test

import com.aice.appstartfaster.task.AppStartTask
import java.util.*

class YeezyTask : AppStartTask() {
    override fun run() {

    }

    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {

        val dependsTaskList: MutableList<Class<out AppStartTask?>> = ArrayList()
        dependsTaskList.add(MainShortTask::class.java)
        dependsTaskList.add(DuPumpTask::class.java)
        return dependsTaskList
    }

    override fun isRunOnMainThread() = false
}