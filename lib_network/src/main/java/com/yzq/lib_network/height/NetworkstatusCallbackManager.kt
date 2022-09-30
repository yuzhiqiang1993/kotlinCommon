package com.yzq.lib_network.height

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.yzq.lib_application.AppContext
import com.yzq.lib_network.NetworkType
import com.yzq.lib_network.NetworkUtil
import com.yzq.lib_network.OnNetworkStatusChangedListener

/**
 * @description: 网路状态变更管理类，使用于Android 7.0及以上的设备
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/9/28
 * @time   : 18:08
 */

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
object NetworkstatusCallbackManager : NetworkCallback() {

    private val listenersSet by lazy { mutableSetOf<OnNetworkStatusChangedListener>() }

    private var registered: Boolean = false//是否注册过callback
    private var networkType: NetworkType = NetworkType.NETWORK_UNKONW
    private val handler = Handler(Looper.getMainLooper())

    /**
     * 添加监听,有序且唯一
     * @param onNetworkStatusChangedListener OnNetworkStatusChangedListener
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun registerListener(
        onNetworkStatusChangedListener: OnNetworkStatusChangedListener
    ) {

        if (!listenersSet.contains(onNetworkStatusChangedListener)) {
            listenersSet.add(onNetworkStatusChangedListener)
            if (!registered) {
                val connectivityManager =
                    AppContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                connectivityManager.registerDefaultNetworkCallback(this)
                registered = true
            }
        }
    }


    /**
     * 移除监听
     * @param onNetworkStatusChangedListener OnNetworkStatusChangedListener
     */
    fun unRegisterListener(onNetworkStatusChangedListener: OnNetworkStatusChangedListener) {
        if (listenersSet.contains(onNetworkStatusChangedListener)) {
            listenersSet.remove(onNetworkStatusChangedListener)
            if (listenersSet.size == 0) {
                /*没有监听器了 取消注册*/
                val connectivityManager =
                    AppContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                connectivityManager.unregisterNetworkCallback(this)
                registered = false
            }
        }


    }

    /**
     * 清除所有监听
     */
    fun clearNetworkStatusChangedListener() {
        if (listenersSet.size > 0) {
            listenersSet.forEach {
                unRegisterListener(it)
            }
        }
    }

    /**
     * 网络断开连接
     * @param network Network
     */
    override fun onLost(network: Network) {
        dispatchStatus(NetworkType.NETWORK_NO)
    }

    /**
     * 分发网络状态
     * @param networkType NetworkType
     */
    private fun dispatchStatus(networkType: NetworkType) {
        if (listenersSet.size == 0) {
            return
        }
        /*防止重复通知*/
        if (this.networkType.code == networkType.code) {
            return
        }
        this.networkType = networkType
        if (this.networkType == NetworkType.NETWORK_NO) {
            handler.post {
                listenersSet.forEach {
                    it.onDisconnected(this.networkType)
                }
            }
        } else {
            handler.post {
                listenersSet.forEach {
                    it.onConnect(this.networkType)
                }
            }
        }


    }

    /**
     * 网络状态发生变化
     * @param network Network
     * @param networkCapabilities NetworkCapabilities
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        dispatchStatus(NetworkUtil.getNetworkType())
    }
}