package com.yzq.application


/**
 * @description App状态监听器
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

interface AppStateListener {
    /**
     * App切换到了前台
     */
    fun onAppForeground() {}

    /**
     * App切换到了后台
     */
    fun onAppBackground() {}

    /**
     * App退出
     */
    fun onAppExit() {}
}