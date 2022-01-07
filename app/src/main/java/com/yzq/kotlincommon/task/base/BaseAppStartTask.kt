package com.yzq.kotlincommon.task.base

import android.os.Trace
import com.aice.appstartfaster.task.AppStartTask
import com.blankj.utilcode.util.LogUtils

/**
 * @description: 启动任务基类
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/7
 * @time   : 5:41 下午
 */

abstract class BaseAppStartTask : AppStartTask() {
    override fun run() {
        val methordName = Thread.currentThread().getStackTrace()[2].getMethodName();

        LogUtils.i("methordName:${methordName}")
        LogUtils.i("javaClass.canonicalName:${javaClass.canonicalName}")
        Trace.beginSection("${javaClass.canonicalName}.${methordName}")
        LogUtils.i(Thread.currentThread().name)
        taskRun()
        Trace.endSection()
    }

    abstract fun taskRun()

}