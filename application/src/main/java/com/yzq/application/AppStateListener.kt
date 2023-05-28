package com.yzq.application


/**
 * App状态接口
 */
interface AppStateListener {
    fun onAppForeground() {}
    fun onAppBackground() {}
    fun onAppExit() {}
}