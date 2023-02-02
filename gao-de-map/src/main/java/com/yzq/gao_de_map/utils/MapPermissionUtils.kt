package com.yzq.gao_de_map.utils

import android.annotation.SuppressLint
import com.hjq.permissions.Permission
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.common.utils.LocationUtils
import com.yzq.materialdialog.showPositiveCallbackDialog
import com.yzq.permission.getPermissions

/**
 * @description: 定位权限
 * @author : yzq
 * @date : 2018/12/19
 * @time : 16:04
 *
 */


object MapPermissionUtils {

    /*检查定位相关权限*/
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

            if (LocationUtils.isGpsEnabled()) {
                block()
            } else {
                activity.showPositiveCallbackDialog("提示", "该功能需要打开GPS,否则无法使用") {
                    LocationUtils.openGpsSettings()
                }
            }
        }


    }
}
