package com.yzq.kotlincommon.task.mainthread

import com.xeon.baidu.ASRBaiduManager
import com.yzq.appstartup.MainThreadTask

/**
 * @description: 初始化语音识别
 * @author : yuzhiqiang
 */

class InitAsrBaiduTask : MainThreadTask() {
    override fun taskRun() {
//        ASRBaiduManager.init(
//            "117651900",
//            "SZABhR417C2gGNY7qY3hscxo",
//            "ntDsBwGEdf5LT12Ru7LL8CWdled93j8a"
//        )

        ASRBaiduManager.init(
            "117872116",
            "VkqwX0baBNT031Gz2vkPYObh",
            "Htp5isj0GdnSc6MGP9qrPVKnM8PoZF7b"
        )
    }
}