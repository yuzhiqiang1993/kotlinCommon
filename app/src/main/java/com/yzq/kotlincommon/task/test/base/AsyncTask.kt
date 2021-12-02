package com.yzq.kotlincommon.task.test.base

import android.os.Trace
import com.aice.appstartfaster.task.AppStartTask
import com.blankj.utilcode.util.LogUtils
import java.util.concurrent.TimeUnit

open class AsyncTask : AppStartTask() {
    override fun run() {

        /*方法名 */
        val threadName = Thread.currentThread().getStackTrace()[2].getMethodName();
        /**
         * 包名+类名
         */
        val canonicalName = javaClass.canonicalName

//        LogUtils.i("threadName:${threadName}")
//        LogUtils.i("javaClass.canonicalName:${javaClass.canonicalName}")

        Trace.beginSection("${javaClass.canonicalName}.${threadName}")
        TimeUnit.MILLISECONDS.sleep(20)
        LogUtils.i(Thread.currentThread().name)

        Trace.endSection()

    }

    override fun isRunOnMainThread() = false

    override fun needWait() = true
}