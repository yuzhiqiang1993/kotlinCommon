package com.yzq.base.startup.base

/**
 * @description: 在子线程初始化的任务
 *
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2022/1/7
 * @time : 4:39 下午
 */

abstract class WorkThreadTask : BaseAppStartTask() {

    override fun isRunOnMainThread() = false

    override fun needWait() = true
}
