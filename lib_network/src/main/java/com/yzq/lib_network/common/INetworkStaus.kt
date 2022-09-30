package com.yzq.lib_network.common

import com.yzq.lib_network.NetworkType
import com.yzq.lib_network.OnNetworkStatusChangedListener


/**
 * @description: 网络状态接口
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/9/28
 * @time   : 17:58
 */

internal interface INetworkStaus {
    /**
     * 网络是否链接
     * @return Boolean
     */
    fun isConnected(): Boolean

    /**
     * 是否是移动网络
     * @return Boolean
     */
    fun isMobileData(): Boolean

    /**
     * 是否是wifi链接
     *
     * @return Boolean
     */
    fun isWifiConnected(): Boolean

    /**
     * 获取当前网络类型
     * @return NetworkType 网路类型
     * @see NetworkType
     */
    fun getNetworkType(): NetworkType

    /**
     * 注册网络状态监听 (加监听)
     * @param listener OnNetworkStatusChangedListener  监听器
     */
    fun registerNetworkStatusChangedListener(
        listener: OnNetworkStatusChangedListener
    )

    /**
     * 解除注册网络状态监听 (移除监听)
     * @param listener OnNetworkStatusChangedListener
     */
    fun unRegisterNetworkStatusChangedListener(
        listener: OnNetworkStatusChangedListener
    )

    /**
     * 清除所有网络状态监听器
     */
    fun clearNetworkStatusChangedListener()
}