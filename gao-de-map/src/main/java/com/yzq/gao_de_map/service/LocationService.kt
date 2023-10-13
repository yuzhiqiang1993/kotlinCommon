package com.yzq.gao_de_map.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.yzq.application.AppContext
import com.yzq.application.AppManager
import com.yzq.application.AppStateListener
import com.yzq.gao_de_map.GaoDeActivity
import com.yzq.gao_de_map.R
import com.yzq.location_manager.LocationManager
import com.yzq.location_protocol.callback.LocationListener
import com.yzq.location_protocol.core.LocationClient
import com.yzq.location_protocol.data.Location
import com.yzq.logger.Logger
import java.util.concurrent.atomic.AtomicBoolean


/**
 * @description 定位服务
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/12
 * @time    17:33
 */

class LocationService : Service(), AppStateListener {


    private var locationStarted = AtomicBoolean(false)


    private var locationClient: LocationClient? = null


    override fun onCreate() {
        AppManager.addAppStateListener(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Logger.i("onStartCommand")
        if (locationStarted.get()) {
            Logger.i("已经在定位了")
            return super.onStartCommand(intent, flags, startId)
        }
        /*前台服务的一个特点就是要是通知栏*/
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "ForegroundService"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /*Android 8.0开始必须设置channel*/
            val channel = NotificationChannel(
                channelId,
                "获取位置信息",
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    description = "持续获取位置信息中..."
//                    enableVibration(true)//通知出现时是否震动，一般不设置
                }
            notificationManager.createNotificationChannel(channel)
        }


        /*点击跳转页面*/
        var flag = PendingIntent.FLAG_UPDATE_CURRENT
        /*兼容Android31*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flag = flag or PendingIntent.FLAG_IMMUTABLE
        }
        /*如果想点击不同的按钮跳转到不同的页面 可以给不同的requestCode来区分*/
        val pendingIntent =
            PendingIntent.getActivity(AppContext, 1, Intent(this, GaoDeActivity::class.java), flag)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("持续定位")
            .setContentText("持续获取位置信息中...")
            .setWhen(System.currentTimeMillis())//显示通知发生的时间
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingIntent)
            .build()

        /*调用startForegroundService的5秒内必要调用startForeground*/
        startForeground(1, notification)
        LocationManager.enableBackgroundLocation(1, notification)
        locationClient =
            LocationManager.startIntervalLocation(5000,
                object : LocationListener {
                    override fun onReceiveLocation(location: Location) {
                        Logger.i("${location}")
                    }
                })

        locationStarted.compareAndSet(false, true)



        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onDestroy() {
        Logger.i("onDestory")
        locationStarted.compareAndSet(true, false)
        locationClient?.destory()
        AppManager.removeAppStateListener(this)
    }

    override fun onAppExit() {
        stopSelf()
    }


}