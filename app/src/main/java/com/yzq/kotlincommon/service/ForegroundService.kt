package com.yzq.kotlincommon.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.blankj.utilcode.util.LogUtils
import com.yzq.application.AppContext
import com.yzq.application.AppManager
import com.yzq.application.AppStateListener
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.ui.activity.MainActivity


/**
 * @description 前台服务使用示例，主要是为了提升应用进程优先级，防止内存不足被系统杀掉，用户强杀防止不了
 * 前台服务会在通知栏显示通知
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/12
 * @time    11:36
 */

class ForegroundService : Service(), AppStateListener {

    override fun onCreate() {
        LogUtils.i("onCreate")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        LogUtils.i("onStartCommand")/*前台服务的一个特点就是要是通知栏*/
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "ForegroundService"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {/*Android 8.0开始必须设置channel*/
            val channel = NotificationChannel(
                channelId, "前台服务的通知", NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "通知渠道的描述"
                enableVibration(true)//通知出现时是否震动，一般不设置
            }
            notificationManager.createNotificationChannel(channel)
        }


        /*点击跳转页面*/
        var flag = PendingIntent.FLAG_UPDATE_CURRENT/*兼容Android31*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flag = flag or PendingIntent.FLAG_IMMUTABLE
        }/*如果想点击不同的按钮跳转到不同的页面 可以给不同的requestCode来区分*/
        val pendingIntent =
            PendingIntent.getActivity(AppContext, 1, Intent(this, MainActivity::class.java), flag)

        val notification =
            NotificationCompat.Builder(AppContext, channelId).setContentTitle("通知标题")
                .setContentText("通知的内容，Kotlin。。。。")
                .setWhen(System.currentTimeMillis())//显示通知发生的时间
                .setSmallIcon(R.mipmap.ic_launcher_round).setContentIntent(pendingIntent).build()

        /*调用 startForegroundService 的5秒内必要调用startForeground*/
        startForeground(1, notification)

        AppManager.addAppStateListener(this)



        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        LogUtils.i("onDestory")
        AppManager.removeAppStateListener(this)
    }

    override fun onAppExit() {
        stopSelf()
    }

}