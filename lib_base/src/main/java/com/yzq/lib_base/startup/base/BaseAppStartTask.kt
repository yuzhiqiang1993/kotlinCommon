package com.yzq.lib_base.startup.base

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

        var time = System.currentTimeMillis()
        val methordName = Thread.currentThread().stackTrace[2].methodName

//        LogUtils.i("methordName:${methordName}")
//        LogUtils.i("javaClass.canonicalName:${javaClass.canonicalName}")
        Trace.beginSection("${javaClass.canonicalName}.${methordName}")
        LogUtils.i(Thread.currentThread().name)
        taskRun()

        time = System.currentTimeMillis() - time

        LogUtils.i("${javaClass.canonicalName}.${methordName} 耗时 time = $time")
        Trace.endSection()
    }

    abstract fun taskRun()

}