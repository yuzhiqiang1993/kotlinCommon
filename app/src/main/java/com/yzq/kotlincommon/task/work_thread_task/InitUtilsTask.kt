package com.yzq.kotlincommon.task.work_thread_task

import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.yzq.application.BaseApp
import com.yzq.base.BuildConfig
import com.yzq.base.startup.base.WorkThreadTask

/**
 * @description: 初始化 Utils
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2021/11/29
 * @time : 8:14 下午
 */

class InitUtilsTask : WorkThreadTask() {
    override fun taskRun() {
        Utils.init(BaseApp.getInstance())
        val config = LogUtils.getConfig()
            .setLogSwitch(BuildConfig.DEBUG)
            .setGlobalTag(AppUtils.getAppName())
            .setConsoleSwitch(BuildConfig.DEBUG)
            .setGlobalTag("KotlinCommon")

        LogUtils.d(config.toString())
    }
}
