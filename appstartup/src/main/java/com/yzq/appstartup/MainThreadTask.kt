package com.yzq.appstartup

/**
 * @description: 主线程初始化的任务
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

abstract class MainThreadTask : BaseAppStartTask() {
    override fun isRunOnMainThread() = true
}
