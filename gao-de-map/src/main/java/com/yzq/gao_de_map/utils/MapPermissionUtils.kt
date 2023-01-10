package com.yzq.gao_de_map.utils

import android.annotation.SuppressLint
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.OnPermissionPageCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.common.utils.LocationUtils
import com.yzq.materialdialog.showPositiveCallbackDialog
import com.yzq.permission.PermissionNameConvert

/**
 * @description: 定位权限
 * @author : yzq
 * @date : 2018/12/19
 * @time : 16:04
 *
 */

typealias MapPermission = () -> Unit

object MapPermissionUtils {

    /*检查定位相关权限*/
    @SuppressLint("CheckResult")
    fun checkLocationPermission(
        needGps: Boolean = false,
        activity: BaseActivity,
        mapPermission: MapPermission,
    ) {

        XXPermissions
            .with(activity)
            .permission(
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION,
                Permission.ACCESS_BACKGROUND_LOCATION
            )
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    LogUtils.i("onGranted:${permissions}")
                    LogUtils.i("allGranted:${allGranted}")
                    if (allGranted) {
                        if (needGps) {
                            if (LocationUtils.isGpsEnabled()) {
                                mapPermission()
                            } else {
                                activity.showPositiveCallbackDialog("提示", "该功能需要打开GPS,否则无法使用") {
                                    LocationUtils.openGpsSettings()
                                }

                            }
                        } else {
                            mapPermission()
                        }
                    }
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    LogUtils.i("onDenied:${permissions}")
                    LogUtils.i("doNotAskAgain:${doNotAskAgain}")

                    if (doNotAskAgain) {
                        /*是否勾选了不再询问选项*/
                        activity.showPositiveCallbackDialog("提示",
                            "我们需要的${
                                PermissionNameConvert.getPermissionString(activity,
                                    permissions)
                            } 被您拒绝，请手动授予权限，否则无法使用") {
                            XXPermissions.startPermissionActivity(activity,
                                permissions,
                                object : OnPermissionPageCallback {
                                    override fun onGranted() {
                                        mapPermission()
                                    }

                                    override fun onDenied() {
                                        ToastUtils.showShort("权限获取失败")
                                    }

                                })

                        }
                    }

                }

            })


//        activity.getPermissions(
//            PermissionConstants.LOCATION
//        ) {
//            if (needGps) {
//
//                if (LocationUtils.isGpsEnabled()) {
//                    mapPermission()
//                } else {
//                    Toast.makeText(
//                        activity,
//                        "该功能需要获取当前位置信息，请打开GPS",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    LocationUtils.openGpsSettings()
//                }
//            } else {
//                mapPermission()
//            }
//        }
    }
}
