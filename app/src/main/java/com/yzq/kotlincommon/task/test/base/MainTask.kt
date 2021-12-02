package com.yzq.kotlincommon.task.test.base

import android.os.Trace
import com.aice.appstartfaster.task.AppStartTask
import com.blankj.utilcode.util.LogUtils
import java.util.concurrent.TimeUnit

open class MainTask : AppStartTask() {
    override fun run() {
        val threadName = Thread.currentThread().getStackTrace()[2].getMethodName();

//        LogUtils.i("threadName:${threadName}")
//        LogUtils.i("javaClass.canonicalName:${javaClass.canonicalName}")

        Trace.beginSection("${javaClass.canonicalName}.${threadName}")
        TimeUnit.MILLISECONDS.sleep(20)
        LogUtils.i(Thread.currentThread().name)
        Trace.endSection()
    }

    override fun isRunOnMainThread() = true
}