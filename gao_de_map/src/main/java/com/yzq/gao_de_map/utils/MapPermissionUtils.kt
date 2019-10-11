package com.yzq.gao_de_map.utils

import android.annotation.SuppressLint
import android.widget.Toast
import com.yanzhenjie.permission.runtime.Permission
import com.yzq.common.utils.LocationUtils
import com.yzq.lib_base.ui.BaseActivity
import com.yzq.lib_permission.requestPermissions


/**
 * @description: 定位权限
 * @author : yzq
 * @date   : 2018/12/19
 * @time   : 16:04
 *
 */

typealias MapPermission = () -> Unit

object MapPermissionUtils {

    /*检查定位相关权限*/
    @SuppressLint("CheckResult")
    fun checkLocationPermission(
        needGps: Boolean = false,
        activity: BaseActivity,
        mapPermission: MapPermission
    ) {
        activity.requestPermissions(
            Permission.ACCESS_FINE_LOCATION,
            Permission.ACCESS_COARSE_LOCATION,
            Permission.WRITE_EXTERNAL_STORAGE,
            Permission.READ_EXTERNAL_STORAGE,
            Permission.READ_PHONE_STATE

        ) {
            if (needGps) {

                if (LocationUtils.isGpsEnabled()) {
                    mapPermission()
                } else {
                    Toast.makeText(
                        activity,
                        "该功能需要获取当前位置信息，请打开GPS",
                        Toast.LENGTH_LONG
                    ).show()
                    LocationUtils.openGpsSettings()
                }

            } else {

                mapPermission()

            }
        }


    }


}