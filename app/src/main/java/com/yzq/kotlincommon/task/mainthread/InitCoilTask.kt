package com.yzq.kotlincommon.task.mainthread

import com.yzq.appstartup.MainThreadTask
import com.yzq.img.CoilManager

/**
 * @description: Coil 初始化
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

class InitCoilTask : MainThreadTask() {

    override fun taskRun() {
        //初始化图片加载
        CoilManager.init()
    }
}
