package com.yzq.network_status

/**
 * @description: 网络类型
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2022/9/28
 * @time : 10:11
 */

enum class NetworkType(val code: Int, val desc: String) {
    NETWORK_UNKONW(-2, "未知网络"),
    NETWORK_NO(-1, "无网络"),
    NETWORK_ETHERNET(0, "以太网"),
    NETWORK_WIFI(1, "wifi"),
    NETWORK_2G(2, "2G"),
    NETWORK_3G(3, "3G"),
    NETWORK_4G(4, "4G"),
    NETWORK_5G(5, "5G"),
}
