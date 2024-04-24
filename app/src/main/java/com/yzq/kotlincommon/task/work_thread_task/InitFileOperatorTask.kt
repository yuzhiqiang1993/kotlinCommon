package com.yzq.kotlincommon.task.work_thread_task

import ando.file.core.FileOperator
import com.yzq.application.AppManager
import com.yzq.base.startup.base.WorkThreadTask
import com.yzq.kotlincommon.BuildConfig

/**
 * @description: 初始化 Utils
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2021/11/29
 * @time : 8:14 下午
 */

class InitFileOperatorTask : WorkThreadTask() {
    override fun taskRun() {
        FileOperator.init(AppManager.application, BuildConfig.DEBUG)
    }
}
