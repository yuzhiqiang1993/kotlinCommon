package com.yzq.base.startup.base

/**
 * @description: 主线程初始化的任务
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/7
 * @time   : 4:40 下午
 */

abstract class MainThreadTask : BaseAppStartTask() {
    override fun isRunOnMainThread() = true
}