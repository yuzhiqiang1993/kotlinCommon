package com.yzq.network_status

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresPermission
import com.yzq.network_status.common.INetworkStatus
import com.yzq.network_status.height.NetworkHeight
import com.yzq.network_status.legacy.NetworkLegacy


/**
 * @description: 网络相关的工具类
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/9/26
 * @time   : 16:40
 */
object NetworkUtil : INetworkStatus {

    /**
     * 网络是否连接
     * 需要权限： `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     * @param context Context
     * @return Boolean
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun isConnected(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkHeight.isConnected()
        } else {
            NetworkLegacy.isConnected()
        }
    }


    /**
     * 是否是移动流量
     * 需要权限： `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     * @return Boolean
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun isMobileData(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkHeight.isMobileData()
        } else {
            NetworkLegacy.isMobileData()
        }
    }

    /**
     * 是否是wifi连接
     * 需要权限： `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     * @return Boolean
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun isWifiConnected(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkHeight.isWifiConnected()
        } else {
            NetworkLegacy.isWifiConnected()
        }
    }


    /**
     * 获取网络类型
     * 需要申请动态权限  `Manifest.permission.READ_PHONE_STATE`
     * @return NetworkType
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    override fun getNetworkType(): NetworkType {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return NetworkHeight.getNetworkType()
        } else {
            return NetworkLegacy.getNetworkType()
        }

    }

    /**
     * 注册网络状态监听
     * @param listener OnNetworkStatusChangedListener
     */
    override fun registerNetworkStatusChangedListener(
        listener: OnNetworkStatusChangedListener
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NetworkHeight.registerNetworkStatusChangedListener(listener)
        } else {
            NetworkLegacy.registerNetworkStatusChangedListener(listener)
        }
    }

    /**
     * 解除监听
     * @param listener OnNetworkStatusChangedListener
     */
    override fun unRegisterNetworkStatusChangedListener(listener: OnNetworkStatusChangedListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NetworkHeight.unRegisterNetworkStatusChangedListener(listener)
        } else {
            NetworkLegacy.unRegisterNetworkStatusChangedListener(listener)
        }
    }

    /**
     * 清除所有监听
     */
    override fun clearNetworkStatusChangedListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NetworkHeight.clearNetworkStatusChangedListener()
        } else {
            NetworkLegacy.clearNetworkStatusChangedListener()
        }
    }


}