package com.yzq.kotlincommon.task.test

import com.aice.appstartfaster.task.AppStartTask
import com.yzq.kotlincommon.task.test.base.MainTask
import java.util.*

class MainShortTask : MainTask() {

    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {

        val dependsTaskList: MutableList<Class<out AppStartTask?>> = ArrayList()
        dependsTaskList.add(BuglyTask::class.java)
        dependsTaskList.add(HeinerTask::class.java)
        return dependsTaskList
    }

}