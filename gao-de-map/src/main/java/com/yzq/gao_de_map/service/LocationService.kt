package com.yzq.gao_de_map.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.amap.api.location.*
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.yzq.application.AppContext
import com.yzq.application.BaseApp
import com.yzq.gao_de_map.GaoDeActivity
import com.yzq.gao_de_map.R
import java.util.concurrent.atomic.AtomicBoolean


/**
 * @description 定位服务
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/12
 * @time    17:33
 */

class LocationService : Service(), AMapLocationListener, BaseApp.AppExitListener {


    /*正常一次定位*/
    private var locationStarted = AtomicBoolean(false)

    private val locationClient by lazy {
        /*要先调用隐私合规方法  否则必崩*/
        AMapLocationClient.updatePrivacyShow(AppContext, true, true)
        AMapLocationClient.updatePrivacyAgree(AppContext, true)
        AMapLocationClient(AppContext)
    }

    override fun onCreate() {
        /*初始化定位*/
        locationClient.setLocationOption(initOption())
        locationClient.setLocationListener(this)

        BaseApp.getInstance().addAppExitListener(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        LogUtils.i("onStartCommand")
        if (locationStarted.get()) {
            LogUtils.i("已经在定位了")
            return super.onStartCommand(intent, flags, startId)
        }


        /*前台服务的一个特点就是要是通知栏*/
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        val channelId = "ForegroundService"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /*Android 8.0开始必须设置channel*/
            val channel = NotificationChannel(channelId,
                "获取位置信息",
                NotificationManager.IMPORTANCE_HIGH)
                .apply {
                    description = "持续获取位置信息,以便于用户知晓你的送餐位置"
                    enableVibration(true)//通知出现时是否震动，一般不设置
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
            .setContentText("持续获取位置信息,以便于用户知晓你的送餐位置...")
            .setWhen(System.currentTimeMillis())//显示通知发生的时间
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingIntent)
            .build()

        /*调用startForegroundService的5秒内必要调用startForeground*/
        startForeground(1, notification)

        locationClient.startLocation()
        locationStarted.compareAndSet(false, true)

        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 持续定位
     *
     * @return
     */
    private fun initOption(): AMapLocationClientOption {
        val mOption = AMapLocationClientOption()
        mOption.isOnceLocation = false
        mOption.interval = 5 * 1000
        return mOption
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onDestroy() {
        LogUtils.i("onDestory")
        locationStarted.compareAndSet(true, false)
        locationClient.stopLocation()
        locationClient.onDestroy()
        BaseApp.getInstance().removeAppExitListener(this)

    }

    override fun onLocationChanged(location: AMapLocation) {
        val sb = StringBuffer()
        // errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明

        if (location.errorCode == 0) {
            sb.append("定位成功" + "\n")
            sb.append("定位类型: " + location.locationType + "\n")
            sb.append("经    度    : " + location.longitude + "\n")
            sb.append("纬    度    : " + location.latitude + "\n")
            sb.append("精    度    : " + location.accuracy + "米" + "\n")
            sb.append("提供者    : " + location.provider + "\n")

            sb.append("速    度    : " + location.speed + "米/秒" + "\n")
            sb.append("角    度    : " + location.bearing + "\n")
            // 获取当前提供定位服务的卫星个数
            sb.append("星    数    : " + location.satellites + "\n")
            sb.append("国    家    : " + location.country + "\n")
            sb.append("省            : " + location.province + "\n")
            sb.append("市            : " + location.city + "\n")
            sb.append("城市编码 : " + location.cityCode + "\n")
            sb.append("区            : " + location.district + "\n")
            sb.append("区域 码   : " + location.adCode + "\n")
            sb.append("地    址    : " + location.address + "\n")
            sb.append("兴趣点    : " + location.poiName + "\n")
            // 定位完成的时间
            sb.append("定位时间: " + TimeUtils.millis2String(location.time) + "\n")
        } else {
            // 定位失败
            sb.append("定位失败" + "\n")
            sb.append("错误码:" + location.errorCode + "\n")
            sb.append("错误信息:" + location.errorInfo + "\n")
            sb.append("错误描述:" + location.locationDetail + "\n")
        }
        sb.append("***定位质量报告***").append("\n")
        sb.append("* WIFI开关：").append(if (location.locationQualityReport.isWifiAble) "开启" else "关闭")
            .append("\n")
        sb.append("* GPS状态：").append(getGPSStatusString(location.locationQualityReport.gpsStatus))
            .append("\n")
        sb.append("* GPS星数：").append(location.locationQualityReport.gpsSatellites).append("\n")
        sb.append("****************").append("\n")
        // 定位之后的回调时间
        sb.append("回调时间: " + TimeUtils.getNowString() + "\n")

        // 解析定位结果，
        val result = sb.toString()

        LogUtils.i(result)
    }

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private fun getGPSStatusString(statusCode: Int): String {
        var str = ""
        when (statusCode) {
            AMapLocationQualityReport.GPS_STATUS_OK -> str = "GPS状态正常"
            AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER ->
                str =
                    "手机中没有GPS Provider，无法进行GPS定位"
            AMapLocationQualityReport.GPS_STATUS_OFF -> str = "GPS关闭，建议开启GPS，提高定位质量"
            AMapLocationQualityReport.GPS_STATUS_MODE_SAVING ->
                str =
                    "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量"
            AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION -> str = "没有GPS定位权限，建议开启gps定位权限"
        }
        return str
    }


    override fun onAppexit() {
        stopSelf()
    }

}