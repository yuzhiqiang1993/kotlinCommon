package com.yzq.kotlincommon.task.main_thread_task

import com.drake.statelayout.StateConfig
import com.yzq.base.startup.base.MainThreadTask


/**
 * @description 初始化状态布局
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/11/18
 * @time    14:40
 */
class InitStateLayoutConfigTask : MainThreadTask() {
    override fun taskRun() {
        StateConfig
            .apply {


            }
    }
}
