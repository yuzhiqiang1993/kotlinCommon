package com.yzq.kotlincommon.task.test

import com.aice.appstartfaster.task.AppStartTask
import com.yzq.kotlincommon.task.test.base.MainTask
import java.util.*

class UiUtilTask : MainTask() {
    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {
        val dependsTaskList: MutableList<Class<out AppStartTask?>> = ArrayList()
        dependsTaskList.add(PoizonImageTask::class.java)
        dependsTaskList.add(BPMTasK::class.java)

        return dependsTaskList
    }
}