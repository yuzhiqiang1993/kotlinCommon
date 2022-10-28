package com.yzq.network_status.common

import android.telephony.TelephonyManager
import com.yzq.network_status.NetworkType


/**
 * @description: 移动网络类型转换
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/9/28
 * @time   : 14:18
 */

internal object MobileNetworkType {

    /**
     * 将蜂窝网转为NetworkType
     * @param networkTypeNum Int
     * @return NetworkType
     */
    fun convertToNetworkType(networkTypeNum: Int): NetworkType {
        return when (networkTypeNum) {
            TelephonyManager.NETWORK_TYPE_GPRS,
            TelephonyManager.NETWORK_TYPE_EDGE,
            TelephonyManager.NETWORK_TYPE_CDMA,
            TelephonyManager.NETWORK_TYPE_1xRTT,
            TelephonyManager.NETWORK_TYPE_IDEN -> {
                NetworkType.NETWORK_2G
            }
            TelephonyManager.NETWORK_TYPE_UMTS,
            TelephonyManager.NETWORK_TYPE_EVDO_0,
            TelephonyManager.NETWORK_TYPE_EVDO_A,
            TelephonyManager.NETWORK_TYPE_EVDO_B,
            TelephonyManager.NETWORK_TYPE_HSPA,
            TelephonyManager.NETWORK_TYPE_HSPAP,
            TelephonyManager.NETWORK_TYPE_HSDPA,
            TelephonyManager.NETWORK_TYPE_HSUPA,
            TelephonyManager.NETWORK_TYPE_EHRPD -> {
                NetworkType.NETWORK_3G
            }
            TelephonyManager.NETWORK_TYPE_LTE -> {
                NetworkType.NETWORK_4G
            }
            TelephonyManager.NETWORK_TYPE_NR -> {
                NetworkType.NETWORK_5G
            }
            TelephonyManager.NETWORK_TYPE_UNKNOWN -> {
                NetworkType.NETWORK_UNKONW
            }
            else -> {
                NetworkType.NETWORK_UNKONW
            }
        }
    }

}