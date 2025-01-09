package com.yzq.kotlincommon.task.mainthread

import com.tencent.mmkv.MMKV
import com.yzq.application.AppContext
import com.yzq.base.startup.base.MainThreadTask


/**
 * @description MMKV
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/11/18
 * @time    11:34
 */

class InitMMKVTask : MainThreadTask() {
    override fun taskRun() {
        MMKV.initialize(AppContext)
    }
}