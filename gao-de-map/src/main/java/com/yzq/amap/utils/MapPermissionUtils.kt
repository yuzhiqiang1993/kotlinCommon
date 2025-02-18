package com.yzq.amap.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import com.hjq.permissions.Permission
import com.yzq.application.AppContext
import com.yzq.baseui.BaseActivity
import com.yzq.dialog.PromptDialog
import com.yzq.permission.getPermissions

/**
 * @description: 定位权限
 * @author : yzq
 * @date : 2018/12/19
 * @time : 16:04
 *
 */


object MapPermissionUtils {
    /**
     * 检查定位相关权限
     * @param activity BaseActivity
     * @param block () -> Unit
     */
    @SuppressLint("CheckResult")
    fun checkLocationPermission(
        activity: BaseActivity,
        block: () -> Unit,
    ) {
        activity.getPermissions(
            Permission.ACCESS_FINE_LOCATION,
            Permission.ACCESS_COARSE_LOCATION,
            Permission.ACCESS_BACKGROUND_LOCATION
        ) {

            if (isGPSEnabled(activity)) {
                block()
            } else {

                PromptDialog(activity).apply {
                    content("该功能需要打开GPS,否则无法使用")
                }.positiveBtn("去开启") { v -> openGpsSettings(activity) }

            }
        }


    }

    @SuppressLint("WrongConstant")
    fun isGPSEnabled(context: Context = AppContext): Boolean {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


    fun openGpsSettings(context: Context = AppContext) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(intent)
    }
}
