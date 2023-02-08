package com.yzq.gao_de_map

import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.yzq.application.AppContext


/**
 * @description 定位管理
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/13
 * @time    09:59
 */

object LocationManager {

    /**
     * 返回一个新的签到场景的定位客户端
     *
     * @param option 配置项
     * @return
     */
    fun newSigninLocationClient(option: AMapLocationClientOption = defaultSigninOption()): AMapLocationClient =
        createNewLocationClient(option)

    /**
     * 默认单次定位的配置
     * https://lbs.amap.com/api/android-location-sdk/guide/android-location/getlocation#configure
     * @return
     */
    private fun defaultSigninOption(): AMapLocationClientOption {
        val mOption = AMapLocationClientOption()
        mOption.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        return mOption
    }


    /**
     * 返回一个新的持续定位客户端
     *
     * @param interval
     * @param option
     */
    fun newIntervalLocationClient(
        interval: Long,
        option: AMapLocationClientOption = defaultIntervalOption(interval),
    ) = createNewLocationClient(option)


    /**
     * 创建一个新的定位客户端，配置项自己定义
     *
     * @param option
     * @return
     */
    private fun createNewLocationClient(option: AMapLocationClientOption): AMapLocationClient {
        AMapLocationClient.updatePrivacyShow(AppContext, true, true)
        AMapLocationClient.updatePrivacyAgree(AppContext, true)
        val locationClient = AMapLocationClient(AppContext)
        locationClient.setLocationOption(option)
        return locationClient
    }

    /**
     * 默认持续定位的配置
     *
     * @param interval 间隔时间
     * @return
     */
    private fun defaultIntervalOption(interval: Long): AMapLocationClientOption {
        val option = AMapLocationClientOption()
        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.Transport//出行场景
        option.isOnceLocation = false
        option.interval = interval
        return option
    }

    fun destoryLocationClient(singnInLocationClient: AMapLocationClient?) {
        singnInLocationClient?.stopLocation()
        singnInLocationClient?.onDestroy()
    }


}