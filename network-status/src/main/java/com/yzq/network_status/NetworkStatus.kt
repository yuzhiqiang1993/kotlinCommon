package com.yzq.network_status

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.lifecycle.LifecycleOwner
import com.yzq.application.AppContext
import com.yzq.logger.LogCat
import com.yzq.network_status.common.INetworkStatus
import com.yzq.network_status.height.NetworkHeight
import com.yzq.network_status.legacy.NetworkLegacy

/**
 * @description: 网络状态
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2022/9/26
 * @time : 16:40
 */
object NetworkStatus : INetworkStatus {

    /**
     * 网络是否连接
     * 需要权限： `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     * @param context Context
     * @return Boolean
     */
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
    override fun getNetworkType(): NetworkType {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && AppContext.checkSelfPermission(
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            LogCat.e("没有 android.permission.ACCESS_NETWORK_STATE 权限,请先申请")
            return NetworkType.PERMISSION_DENIED
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NetworkHeight.getNetworkType()
        } else {
            NetworkLegacy.getNetworkType()
        }
    }

    /**
     * 注册网络状态监听
     * @param listener OnNetworkStatusChangedListener
     */
    override fun registerNetworkStatusChangedListener(
        listener: OnNetworkStatusChangedListener,
        lifecycleOwner: LifecycleOwner?,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NetworkHeight.registerNetworkStatusChangedListener(listener, lifecycleOwner)
        } else {
            NetworkLegacy.registerNetworkStatusChangedListener(listener, lifecycleOwner)
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
