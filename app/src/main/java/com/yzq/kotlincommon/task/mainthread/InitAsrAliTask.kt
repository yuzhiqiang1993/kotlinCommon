package com.yzq.kotlincommon.task.mainthread

import com.yzq.ali.asr.AsrAliManager
import com.yzq.appstartup.MainThreadTask
import com.yzq.util.ext.getAndroidId

/**
 * @description: 阿里的语音识别
 * @author : yuzhiqiang
 */

class InitAsrAliTask : MainThreadTask() {
    override fun taskRun() {

        AsrAliManager.init(
            "bz2bd70ewrgiJCrj", "4120e4547a624866a451d1d1b31ffd8d", getAndroidId()
        )
    }
}