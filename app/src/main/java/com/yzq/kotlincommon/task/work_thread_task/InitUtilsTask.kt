package com.yzq.kotlincommon.task.work_thread_task

import com.blankj.utilcode.util.*
import com.yzq.lib_base.BaseApp
import com.yzq.lib_base.BuildConfig
import com.yzq.lib_base.startup.base.WorkThreadTask

/**
 * @description: 初始化 Utils
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/11/29
 * @time   : 8:14 下午
 */

class InitUtilsTask : WorkThreadTask() {
    override fun taskRun() {
        Utils.init(BaseApp.INSTANCE)
        val config = LogUtils.getConfig()
            .setLogSwitch(BuildConfig.DEBUG)
            .setGlobalTag(AppUtils.getAppName())
            .setConsoleSwitch(BuildConfig.DEBUG)

        LogUtils.d(config.toString())
    }

}