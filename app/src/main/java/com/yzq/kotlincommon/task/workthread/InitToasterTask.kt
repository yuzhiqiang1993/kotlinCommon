package com.yzq.kotlincommon.task.workthread

import com.hjq.toast.Toaster
import com.yzq.application.AppManager
import com.yzq.appstartup.WorkThreadTask

/**
 * @description: 初始化 Utils
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2021/11/29
 * @time : 8:14 下午
 */

class InitToasterTask : WorkThreadTask() {
    override fun taskRun() {
        Toaster.init(AppManager.application)
    }
}
