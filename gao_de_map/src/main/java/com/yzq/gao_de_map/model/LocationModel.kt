package com.yzq.gao_de_map.model

import android.annotation.SuppressLint
import android.widget.Toast
import com.amap.api.location.*
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.yanzhenjie.permission.Permission
import com.yzq.common.AppContext
import com.yzq.common.BaseApp
import com.yzq.common.permission.PermissionRequester
import com.yzq.common.utils.LocationUtils
import com.yzq.gao_de_map.data.LocationBean
import com.yzq.gao_de_map.view.LocationView
import javax.inject.Inject


/**
 * @description: 定位模块
 * @author : yzq
 * @date   : 2018/11/12
 * @time   : 18:02
 *
 */

class LocationModel @Inject constructor() : AMapLocationListener {

    private var locationClient: AMapLocationClient? = null
    private var view: LocationView? = null

    /*初始化定位*/
    fun initLocation(view: LocationView) {
        this.view = view


        if (locationClient == null) {
            synchronized(AMapLocationClient::class.java) {
                if (locationClient == null) {
                    locationClient = AMapLocationClient(AppContext)
                }
            }
        }

        locationClient!!.setLocationOption(initOption())
        locationClient!!.setLocationListener(this)


    }


    private fun initOption(): AMapLocationClientOption {
        val mOption = AMapLocationClientOption()
        mOption.locationMode =
                AMapLocationClientOption.AMapLocationMode.Hight_Accuracy//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.isGpsFirst = true//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.httpTimeOut = 30000//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.interval = 2000//可选，设置定位间隔。默认为2秒
        mOption.isNeedAddress = true//可选，设置是否返回逆地理地址信息。默认是true
        mOption.isOnceLocation = false//可选，设置是否单次定位。默认是false
        mOption.isOnceLocationLatest = false//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP)//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.isSensorEnable = false//可选，设置是否使用传感器。默认是false
        mOption.isWifiScan = true //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.isLocationCacheEnable = true //可选，设置是否使用缓存定位，默认为true
        return mOption

    }


    /*获取定位相关权限*/
    @SuppressLint("CheckResult")
    fun checkLocationPermission() {

        PermissionRequester.request(
            Permission.ACCESS_FINE_LOCATION
            , Permission.ACCESS_COARSE_LOCATION
            , Permission.WRITE_EXTERNAL_STORAGE
            , Permission.READ_EXTERNAL_STORAGE
            , Permission.READ_PHONE_STATE
        ).subscribe {
            if (LocationUtils.isGpsEnabled()) {

                view?.startLocation()
            } else {
                Toast.makeText(AppContext, "该功能需要获取当前位置信息，请打开GPS", Toast.LENGTH_LONG).show()
                LocationUtils.openGpsSettings()
            }

        }


    }

    /*开始定位*/

    fun startLocation() {
        locationClient?.startLocation()
    }


    override fun onLocationChanged(location: AMapLocation) {


        val sb = StringBuffer()
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明

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
            //定位完成的时间
            sb.append("定位时间: " + TimeUtils.millis2String(location.time) + "\n")


            var locationBean = LocationBean()

            locationBean.longitude = location.longitude
            locationBean.latitude = location.latitude
            locationBean.country = location.country
            locationBean.province = location.province
            locationBean.city = location.city
            locationBean.cityCode = location.cityCode
            locationBean.district = location.district
            locationBean.districtCode = location.adCode
            locationBean.address = location.address


            view?.locationSuccess(locationBean)


        } else {
            //定位失败
            sb.append("定位失败" + "\n")
            sb.append("错误码:" + location.errorCode + "\n")
            sb.append("错误信息:" + location.errorInfo + "\n")
            sb.append("错误描述:" + location.locationDetail + "\n")


            view?.locationFailed(location.locationDetail)


        }
        sb.append("***定位质量报告***").append("\n")
        sb.append("* WIFI开关：").append(if (location.locationQualityReport.isWifiAble) "开启" else "关闭").append("\n")
        sb.append("* GPS状态：").append(getGPSStatusString(location.locationQualityReport.gpsStatus)).append("\n")
        sb.append("* GPS星数：").append(location.locationQualityReport.gpsSatellites).append("\n")
        sb.append("****************").append("\n")
        //定位之后的回调时间
        sb.append("回调时间: " + TimeUtils.getNowString() + "\n")

        //解析定位结果，
        val result = sb.toString()

        LogUtils.i(result)

        
        stopLocation()


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
            AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER -> str = "手机中没有GPS Provider，无法进行GPS定位"
            AMapLocationQualityReport.GPS_STATUS_OFF -> str = "GPS关闭，建议开启GPS，提高定位质量"
            AMapLocationQualityReport.GPS_STATUS_MODE_SAVING -> str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量"
            AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION -> str = "没有GPS定位权限，建议开启gps定位权限"
        }
        return str
    }


    /**
     * 停止定位
     */
    fun stopLocation() {
        locationClient?.stopLocation()

    }

    /**
     * 销毁定位
     */
    fun destroyLocation() {

        locationClient?.stopLocation()
        locationClient?.onDestroy()

    }

}