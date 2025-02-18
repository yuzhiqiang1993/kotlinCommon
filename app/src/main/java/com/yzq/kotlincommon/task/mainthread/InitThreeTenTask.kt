package com.yzq.kotlincommon.task.mainthread

import com.jakewharton.threetenabp.AndroidThreeTen
import com.yzq.application.AppManager
import com.yzq.appstartup.MainThreadTask

/**
 * @description: 日期库初始化
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

class InitThreeTenTask : MainThreadTask() {

    override fun taskRun() {
        //日期库初始化
        AndroidThreeTen.init(AppManager.application)
    }
}
