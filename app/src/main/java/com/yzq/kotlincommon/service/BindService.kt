package com.yzq.kotlincommon.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.yzq.location_manager.LocationManager
import com.yzq.location_protocol.callback.LocationListener
import com.yzq.location_protocol.data.Location
import com.yzq.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


/**
 * @description Service bind
 * bindService 一般用在跟页面进行交互的场景
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/13
 * @time    13:53
 */

class BindService : Service(), LocationListener {

    private var binder = ServiceBinder()

    private var name: String = ""


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.i("onStartCommand")
        /*一般是startService开启任务 bindService做通信*/
        LocationManager.startOnceLocation(this)
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 每次binService都会执行
     *
     * @param intent
     * @return
     */
    override fun onBind(intent: Intent): IBinder {
        Logger.i("onBind")
        return binder
    }


    /*返回给外部的binder 通信的桥梁*/
    inner class ServiceBinder : Binder() {

        private val _locationFlow = MutableStateFlow<Location?>(null)
        val locationFlow = _locationFlow as StateFlow<Location?>


        fun setLocation(location: Location) {
            /*提供数据给外部的方法*/
            _locationFlow.value = location
        }

        fun changeName(newName: String) {
            /*给外部操作Service中的方法*/
            Logger.i("外部调service")
            name = newName
        }

    }

    override fun onReceiveLocation(location: Location) {
        binder.setLocation(location)
    }


    override fun onUnbind(intent: Intent?): Boolean {
        Logger.i("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("onDestroy")
    }
}