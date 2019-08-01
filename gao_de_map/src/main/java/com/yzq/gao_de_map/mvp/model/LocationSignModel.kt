package com.yzq.gao_de_map.mvp.model

import androidx.lifecycle.*
import com.amap.api.location.*
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.yzq.common.AppContext
import com.yzq.gao_de_map.data.LocationBean
import com.yzq.gao_de_map.mvp.view.LocationView
import javax.inject.Inject


/**
 * @description: 定位模块,签到模式
 * @author : yzq
 * @date   : 2018/11/12
 * @time   : 18:02
 *
 */

class LocationSignModel @Inject constructor() : AMapLocationListener,LifecycleObserver {

    private var locationClient: AMapLocationClient? = null
    private var view: LocationView? = null

    /*初始化定位*/
    fun initLocation(view: LocationView,lifecycleOwner: LifecycleOwner) {
        this.view = view

        lifecycleOwner.lifecycle.addObserver(this)

        if (locationClient == null) {
            synchronized(AMapLocationClient::class.java) {
                if (locationClient == null) {
                    locationClient = AMapLocationClient(AppContext)
                    locationClient!!.setLocationOption(initOption())
                    locationClient!!.setLocationListener(this)

                }
            }
        }

    }


    /*签到场景配置*/
    private fun initOption(): AMapLocationClientOption {
        val mOption = AMapLocationClientOption()
        mOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn)

        return mOption

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


            val locationBean = LocationBean()

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
     * 销毁定位
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroyLocation() {
        LogUtils.i("destroyLocation")
        locationClient?.stopLocation()
        locationClient?.onDestroy()

    }




}