package com.yzq.network_status.legacy

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LifecycleOwner
import com.yzq.application.AppContext
import com.yzq.network_status.*
import com.yzq.network_status.common.INetworkStatus
import com.yzq.network_status.common.MobileNetworkType


/**
 * @description: 兼容老版本的api(Android 6.0以下的设备)
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/9/27
 * @time   : 14:49
 */
internal object NetworkLegacy : INetworkStatus {


    /**
     * 获取网络信息  API 29(Android 10) 已弃用
     * @param context Context  上下文
     * @return NetworkInfo?  网络信息，可能为null
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun getActiveNetworkInfo(): NetworkInfo? {
        val connectivityManager =
            AppContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo
    }


    /**
     * 网络是否连接
     * 需要权限：`<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     * @return Boolean
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun isConnected(): Boolean {
        val activeNetworkInfo = getActiveNetworkInfo() ?: return false
        return activeNetworkInfo.isConnected

    }


    /**
     * 是否启用了wifi
     * @return Boolean  true：启用  false：未启用
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    fun getWifiEnabled(): Boolean {
        val wifiManager = AppContext
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isWifiEnabled
    }


    /**
     * 启用/禁用 wifi  Android 10以下版本才能用 不建议通过代码去操作用户的网络 给个提示引导用户开启wifi比较好
     * 需要权限： `<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />`
     * @param enabled Boolean   true:启用  false：禁用
     */
//    @RequiresPermission(permission.CHANGE_WIFI_STATE)
//    fun setWifiEnabled(enabled: Boolean) {
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
//            val wifiManager = YumcAppContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//            if (wifiManager.isWifiEnabled != enabled) {
//                wifiManager.isWifiEnabled = enabled
//            }
//        }
//    }


    /**
     * 是否是移动数据(卡流量)
     * 需要权限： `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     * @return Boolean true：移动数据  false：非移动数据
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun isMobileData(): Boolean {
        val networkInfo: NetworkInfo = getActiveNetworkInfo() ?: return false
        return (networkInfo.isAvailable && networkInfo.type == ConnectivityManager.TYPE_MOBILE)
    }


    /**
     * 是否是wifi链接
     * 需要权限：`<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     * @return Boolean true:是 false:否
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun isWifiConnected(): Boolean {
        val activeNetworkInfo = getActiveNetworkInfo() ?: return false
        return activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
    }


    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    override fun getNetworkType(): NetworkType {
        val activeNetworkInfo = getActiveNetworkInfo() ?: return NetworkType.NETWORK_NO

        return when (activeNetworkInfo.type) {
            ConnectivityManager.TYPE_WIFI -> NetworkType.NETWORK_WIFI
            ConnectivityManager.TYPE_ETHERNET -> NetworkType.NETWORK_ETHERNET
            ConnectivityManager.TYPE_MOBILE -> getMobileNetWorkType()
            else -> NetworkType.NETWORK_UNKONW
        }

    }

    /**
     * 获取蜂窝网的具体类型
     * @return NetworkType
     * @see NetworkType
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    private fun getMobileNetWorkType(): NetworkType {
        val telephonyManager =
            AppContext
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val networkType = telephonyManager.networkType
        return MobileNetworkType.convertToNetworkType(networkType)


    }

    /**
     * 注册监听
     * @param listener OnNetworkStatusChangedListener
     */
    override fun registerNetworkStatusChangedListener(
        listener: OnNetworkStatusChangedListener,
        lifecycleOwner: LifecycleOwner?
    ) {
        NetworkChangedReceiver.registerListener(listener, lifecycleOwner)
    }

    /**
     * 解除监听器
     * @param listener OnNetworkStatusChangedListener
     */
    override fun unRegisterNetworkStatusChangedListener(listener: OnNetworkStatusChangedListener) {
        NetworkChangedReceiver.unRegisterListener(listener)
    }

    /**
     * 清除所有监听
     */
    override fun clearNetworkStatusChangedListener() {
        NetworkChangedReceiver.clearNetworkStatusChangedListener()
    }


}