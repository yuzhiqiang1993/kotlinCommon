package com.yzq.network_status.legacy

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.yzq.application.AppContext
import com.yzq.application.BaseApp
import com.yzq.network_status.NetworkType
import com.yzq.network_status.OnNetworkStatusChangedListener
import com.yzq.network_status.height.NetworkstatusCallbackManager


/**
 * @description: 网络状态变更广播(兼容Android 6.0以下设备)
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/9/28
 * @time   : 11:15
 */


object NetworkChangedReceiver : BroadcastReceiver() {

    private val listenersSet by lazy { mutableSetOf<OnNetworkStatusChangedListener>() }

    private var registered: Boolean = false//广播是否处于注册状态

    private var networkType: NetworkType = NetworkType.NETWORK_UNKONW

    /**
     * 添加监听,有序且唯一
     * @param onNetworkStatusChangedListener OnNetworkStatusChangedListener
     */
    fun registerListener(
        onNetworkStatusChangedListener: OnNetworkStatusChangedListener,
        lifecycleOwner: LifecycleOwner? = null
    ) {
        listenersSet.add(onNetworkStatusChangedListener)
        if (!registered) {
            val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            BaseApp.INSTANCE.registerReceiver(this, intentFilter)
            registered = true
        }

        lifecycleOwner?.run {
            lifecycle.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        NetworkstatusCallbackManager.unRegisterListener(
                            onNetworkStatusChangedListener
                        )
                    }
                }

            })
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
                AppContext.unregisterReceiver(this)
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


    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == null) {
            return
        }
        if (listenersSet.size == 0) {
            return
        }
        if (intent.action!!.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            val networkType = NetworkLegacy.getNetworkType()
            /*防止重复通知*/
            if (this.networkType.code == networkType.code) {
                return
            }
            this.networkType = networkType
            if (networkType.code == NetworkType.NETWORK_NO.code) {
                listenersSet.forEach {
                    it.onDisconnected(this.networkType)
                }
            } else {
                listenersSet.forEach {
                    it.onConnect(this.networkType)
                }
            }
        }
    }
}