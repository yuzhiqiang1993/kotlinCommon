package com.yzq.appstartup

import android.os.Trace
import com.aice.appstartfaster.task.AppStartTask
import com.yzq.logger.Logger

/**
 * @description: 启动任务基类
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

abstract class BaseAppStartTask : AppStartTask() {
    override fun run() {

        var time = System.currentTimeMillis()
        val methordName = Thread.currentThread().stackTrace[2].methodName

//        Logger.i("methordName:${methordName}")
//        Logger.i("javaClass.canonicalName:${javaClass.canonicalName}")
        Trace.beginSection("${javaClass.canonicalName}.$methordName")
        Logger.i(Thread.currentThread().name)
        taskRun()

        time = System.currentTimeMillis() - time

        Logger.i("${javaClass.canonicalName}.$methordName 耗时 time = $time")
        Trace.endSection()
    }

    abstract fun taskRun()
}
