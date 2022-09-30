package com.yzq.lib_network


/**
 * @description: 网络状态发生变化监听接口
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/9/28
 * @time   : 09:47
 */

interface OnNetworkStatusChangedListener {
    /**
     * 网络已连接
     * @param networkType NetworkType
     */
    fun onConnect(networkType: NetworkType)


    /**
     * 网络断开连接
     */
    fun onDisconnected(networkType: NetworkType)


}