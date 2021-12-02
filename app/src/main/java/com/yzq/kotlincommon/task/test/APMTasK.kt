package com.yzq.kotlincommon.task.test

import com.aice.appstartfaster.task.AppStartTask
import com.yzq.kotlincommon.task.test.base.AsyncTask
import java.util.*

class APMTasK : AsyncTask() {
    override fun run() {
    }

    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {

        val dependsTaskList: MutableList<Class<out AppStartTask?>> = ArrayList()

        dependsTaskList.add(MainShortTask::class.java)
        return dependsTaskList
    }

}