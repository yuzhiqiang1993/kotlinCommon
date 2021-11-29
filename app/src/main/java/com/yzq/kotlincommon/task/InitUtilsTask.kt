package com.yzq.kotlincommon.task

import com.aice.appstartfaster.task.AppStartTask
import com.blankj.utilcode.util.*
import com.yzq.lib_base.BaseApp
import com.yzq.lib_base.BuildConfig

/**
 * @description: 初始化 Utils
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/11/29
 * @time   : 8:14 下午
 */

class InitUtilsTask : AppStartTask() {
    override fun run() {
        Utils.init(BaseApp.INSTANCE)
        val config = LogUtils.getConfig()
            .setLogSwitch(BuildConfig.DEBUG)
            .setGlobalTag(AppUtils.getAppName())
            .setConsoleSwitch(BuildConfig.DEBUG)

        LogUtils.d(config.toString())
    }

    override fun needWait() = true

    override fun isRunOnMainThread() = false
}