package com.yzq.appstartup


/**
 * @description: 需要在子线程初始化的task
 * @author : yuzhiqiang
 */
abstract class WorkThreadTask : BaseAppStartTask() {

    override fun isRunOnMainThread() = false

    override fun needWait() = true
}
