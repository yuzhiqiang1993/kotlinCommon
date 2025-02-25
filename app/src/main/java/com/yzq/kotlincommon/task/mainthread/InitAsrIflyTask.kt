package com.yzq.kotlincommon.task.mainthread

import com.yzq.appstartup.MainThreadTask
import com.yzq.ifly.AsrIflyManager

/**
 * @description: 初始化语音识别
 * @author : yuzhiqiang
 */

class InitAsrIflyTask : MainThreadTask() {
    override fun taskRun() {
        AsrIflyManager.init("d0cd63d9")
    }
}