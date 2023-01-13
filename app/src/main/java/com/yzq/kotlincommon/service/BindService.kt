package com.yzq.kotlincommon.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.blankj.utilcode.util.LogUtils
import com.yzq.gao_de_map.LocationManager
import com.yzq.gao_de_map.LocationResultListener
import com.yzq.gao_de_map.ext.setLocationResultListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


/**
 * @description Service bind
 * bindService 一般用在跟页面进行交互的场景
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/13
 * @time    13:53
 */

class BindService : Service(), LocationResultListener {

    private var binder = ServiceBinder()

    private var name: String = ""

    private var newSigninLocationClient: AMapLocationClient? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.i("onStartCommand")
        /*一般是startService开启任务 bindService做通信*/
        startLocation()
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 每次binService都会执行
     *
     * @param intent
     * @return
     */
    override fun onBind(intent: Intent): IBinder {
        LogUtils.i("onBind")
        return binder
    }

    private fun startLocation() {
        if (newSigninLocationClient == null) {
            newSigninLocationClient = LocationManager.newSigninLocationClient()
                .apply { setLocationResultListener(this@BindService) }
        }
        newSigninLocationClient?.startLocation()
    }


    /*返回给外部的binder 通信的桥梁*/
    inner class ServiceBinder : Binder() {

        private val _locationFlow = MutableStateFlow<AMapLocation?>(null)
        val locationFlow = _locationFlow as StateFlow<AMapLocation?>


        fun setLocation(location: AMapLocation) {
            /*提供数据给外部的方法*/
            _locationFlow.value = location
        }

        fun changeName(newName: String) {
            /*给外部操作Service中的方法*/
            LogUtils.i("外部调service")
            name = newName
        }

    }

    override fun onSuccess(location: AMapLocation) {
        binder.setLocation(location)
    }

    override fun onFailed(location: AMapLocation) {
        binder.setLocation(location)
    }


    override fun onUnbind(intent: Intent?): Boolean {
        LogUtils.i("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.i("onDestroy")
        LocationManager.destoryLocationClient(newSigninLocationClient)
    }
}