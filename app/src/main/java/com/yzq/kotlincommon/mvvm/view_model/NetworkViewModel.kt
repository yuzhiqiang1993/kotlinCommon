package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.yzq.lib_base.view_model.BaseViewModel
import com.yzq.lib_network_status.NetworkType
import com.yzq.lib_network_status.NetworkUtil
import com.yzq.lib_network_status.OnNetworkStatusChangedListener


/**
 * @description NetworkViewModel
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/9/30
 * @time    11:28
 */

class NetworkViewModel : BaseViewModel(), OnNetworkStatusChangedListener {


    private val _networkStatusLiveData by lazy { MutableLiveData<NetworkType>() }
    val networkStatusLiveData: LiveData<NetworkType> = _networkStatusLiveData


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        LogUtils.i("onStateChanged:$event")
        if (event == Lifecycle.Event.ON_CREATE) {
            LogUtils.i("注册")
            NetworkUtil.registerNetworkStatusChangedListener(this)
        } else if (event == Lifecycle.Event.ON_DESTROY) {
            LogUtils.i("解绑")
            NetworkUtil.unRegisterNetworkStatusChangedListener(this)
        }
    }

    override fun onConnect(networkType: NetworkType) {
        LogUtils.i("onConnect:${networkType}")
        _networkStatusLiveData.value = networkType
    }

    override fun onDisconnected(networkType: NetworkType) {
        LogUtils.i("onDisconnected:${networkType}")
        _networkStatusLiveData.value = networkType
    }

}