package com.yzq.kotlincommon.task.test

import com.aice.appstartfaster.task.AppStartTask
import com.yzq.kotlincommon.task.test.base.MainTask
import java.util.*

class RestClientTask : MainTask() {

    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {
        val dependsTaskList: MutableList<Class<out AppStartTask?>> = ArrayList()
        dependsTaskList.add(DataCollectTask::class.java)
        dependsTaskList.add(BPMTasK::class.java)
        dependsTaskList.add(OaidTask::class.java)

        return dependsTaskList
    }
}