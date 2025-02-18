package com.yzq.kotlincommon.task.mainthread

import com.xeon.asr_demo.ASRManager
import com.yzq.application.AppManager
import com.yzq.appstartup.MainThreadTask

/**
 * @description: 初始化语音识别
 * @author : yuzhiqiang
 */

class InitAsrTask : MainThreadTask() {
    override fun taskRun() {
        ASRManager.init(AppManager.application)
    }
}