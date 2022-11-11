package com.yzq.network_status.height

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LifecycleOwner
import com.yzq.application.AppContext
import com.yzq.network_status.NetworkType
import com.yzq.network_status.OnNetworkStatusChangedListener
import com.yzq.network_status.common.INetworkStatus
import com.yzq.network_status.common.MobileNetworkType


/**
 * @description: 网络状态相关,适用于Android 6.0 以上的设备
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/9/27
 * @time   : 15:15
 */

internal object NetworkHeight : INetworkStatus {

    /**
     * 获取网络信息
     * @return NetworkCapabilities?
     */
    @RequiresApi(Build.VERSION_CODES.M)
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun getNetworkCapabilities(context: Context): NetworkCapabilities? {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities
    }


    /**
     * 网络是否连接(连网了但网络不一定可用)
     * 需要权限：`<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     * @return Boolean
     */
    @RequiresApi(Build.VERSION_CODES.M)
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun isConnected(): Boolean {
        val capabilities =
            getNetworkCapabilities(AppContext) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


    /**
     * 网络是否可用
     * @return Boolean
     */
    @RequiresApi(Build.VERSION_CODES.M)
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetAvailable(): Boolean {
        val capabilities =
            getNetworkCapabilities(AppContext) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }


    /**
     * 是否是移动数据(手机流量)
     * 需要权限：`<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     * @return Boolean
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun isMobileData(): Boolean {
        val networkCapabilities =
            getNetworkCapabilities(AppContext) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    /**
     * 是否是wifi
     * 需要权限：`<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     * @return Boolean
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun isWifiConnected(): Boolean {
        val networkCapabilities =
            getNetworkCapabilities(AppContext) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    /**
     * 获取网络类型，Android 7.0及以上机型
     * @return NetworkType
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    @RequiresApi(Build.VERSION_CODES.N)
    override fun getNetworkType(): NetworkType {
        /*无网络*/
        val networkCapabilities = getNetworkCapabilities(AppContext)
            ?: return NetworkType.NETWORK_NO

        /*无网络*/
        if (!networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            return NetworkType.NETWORK_NO
        }

        return networkCapabilities.run {
            when {
                /*wifi*/
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.NETWORK_WIFI
                /*以太网*/
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkType.NETWORK_ETHERNET
                /*蜂窝网络(移动数据)*/
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> getMobileNetWorkType()
                /*其他*/
                else -> NetworkType.NETWORK_UNKONW
            }
        }


    }


    /**
     * 获取蜂窝网的具体类型
     * @param context Context
     * @return NetworkType
     */
    @RequiresApi(Build.VERSION_CODES.N)
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    private fun getMobileNetWorkType(): NetworkType {
        val telephonyManager =
            AppContext
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val dataNetworkType = telephonyManager.dataNetworkType
        return MobileNetworkType.convertToNetworkType(dataNetworkType)
    }


    /**
     * 注册网络状态监听
     * @param listener OnNetworkStatusChangedListener
     */
    @RequiresApi(Build.VERSION_CODES.N)
    override fun registerNetworkStatusChangedListener(
        listener: OnNetworkStatusChangedListener,
        lifecycleOwner: LifecycleOwner?
    ) {
        NetworkstatusCallbackManager.registerListener(listener, lifecycleOwner)
    }

    /**
     * 移除网络状态监听
     * @param listener OnNetworkStatusChangedListener
     */
    @RequiresApi(Build.VERSION_CODES.N)
    override fun unRegisterNetworkStatusChangedListener(listener: OnNetworkStatusChangedListener) {
        NetworkstatusCallbackManager.unRegisterListener(listener)
    }

    /**
     * 清除所有监听
     */
    @RequiresApi(Build.VERSION_CODES.N)
    override fun clearNetworkStatusChangedListener() {
        NetworkstatusCallbackManager.clearNetworkStatusChangedListener()
    }

}