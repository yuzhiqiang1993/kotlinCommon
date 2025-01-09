package com.yzq.kotlincommon.task.mainthread

import com.yzq.base.startup.base.MainThreadTask
import com.yzq.img.CoilManager

/**
 * @description: 初始化阿里推送
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2022/1/9
 * @time : 2:57 下午
 */

class InitCoilTask : MainThreadTask() {

    override fun taskRun() {

        //初始化图片加载
        CoilManager.init()
    }
}
