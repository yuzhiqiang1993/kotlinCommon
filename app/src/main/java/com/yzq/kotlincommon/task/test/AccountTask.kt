package com.yzq.kotlincommon.task.test

import com.aice.appstartfaster.task.AppStartTask
import com.yzq.kotlincommon.task.test.base.AsyncTask
import java.util.*

class AccountTask : AsyncTask() {

    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {
        val dependsTaskList: MutableList<Class<out AppStartTask?>> = ArrayList()
        dependsTaskList.add(SmAntiTask::class.java)
        dependsTaskList.add(PoizonAnalyzeTask::class.java)
        dependsTaskList.add(RestClientTask::class.java)

        return dependsTaskList
    }
}