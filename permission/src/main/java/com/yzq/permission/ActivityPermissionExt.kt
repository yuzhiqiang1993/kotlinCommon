package com.yzq.permission

import androidx.activity.ComponentActivity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.OnPermissionPageCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.yzq.materialdialog.showPositiveCallbackDialog


/**
 * @description 快速获取权限
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/10
 * @time    17:06
 */

fun ComponentActivity.getPermissions(
    vararg permissions: String,
    permissionGranted: () -> Unit,
) {
    /*如果已经有权限，直接执行逻辑*/
    if (permissionsGranted(*permissions)) {
        LogUtils.i("已有权限：$permissions")
        permissionGranted.invoke()
        return
    }

    XXPermissions.setCheckMode(BuildConfig.DEBUG)

    XXPermissions
        .with(this)
        .permission(permissions)
        .request(object : OnPermissionCallback {
            override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                if (allGranted) {
                    /*权限都申请成功*/
                    permissionGranted.invoke()
                }
            }

            override fun onDenied(deniedPermissions: MutableList<String>, doNotAskAgain: Boolean) {
                LogUtils.i("deniedPermissions:${deniedPermissions}")
                LogUtils.i("doNotAskAgain:${doNotAskAgain}")
                if (doNotAskAgain) {
                    /*用户点了拒绝并勾选了不在提示 弹窗提示用户手动给权限*/
                    showPermissionSettingDialog(
                        deniedPermissions,
                        permissionGranted
                    )
                    return
                }

                /*一些特殊权限比如后台定位 给用户提示选择始终允许*/
                if (deniedPermissions.size == 1) {
                    val deniedPermission: String = deniedPermissions.get(0)

                    if (Permission.ACCESS_BACKGROUND_LOCATION == deniedPermission) {
                        /*如果是申请后台定位权限 提示用户勾选始终允许*/
                        ToastUtils.showShort(R.string.common_permission_background_location_fail_hint)
                    }

                    /*传感器权限只能选择始终允许*/
                    if (Permission.BODY_SENSORS_BACKGROUND == deniedPermission) {
                        ToastUtils.showShort(R.string.common_permission_background_sensors_fail_hint)
                    }
                }

            }

        })

}


/**
 * 跳转到设置页面前手动给权限的图提示弹窗
 *
 * @param deniedPermissions  被拒绝的权限
 * @param permissionGranted  权限授予后要执行的逻辑
 * @receiver
 */
fun ComponentActivity.showPermissionSettingDialog(
    deniedPermissions: MutableList<String>,
    permissionGranted: () -> Unit,
) {


    showPositiveCallbackDialog(
        getString(R.string.common_permission_alert),
        getHintMessage(deniedPermissions)
    ) {
        /*跳转到设置页面*/
        XXPermissions.startPermissionActivity(
            this,
            deniedPermissions,
            object : OnPermissionPageCallback {
                override fun onGranted() {
                    /*在设置页面都给权限了 执行逻辑*/
                    permissionGranted()
                }

                override fun onDenied() {
                    ToastUtils.showShort("权限获取失败")
                }

            })
    }


}

/**
 * 提示纤细 主要针对一些特殊权限有特殊提示
 *
 * @param deniedPermissions
 * @return
 */
private fun ComponentActivity.getHintMessage(
    deniedPermissions: MutableList<String>,
): String {
    var message: String = getString(
        R.string.common_permission_message,
        PermissionNameConvert.getPermissionString(this, deniedPermissions)
    )
    if (deniedPermissions.size == 1) {
        val deniedPermission: String = deniedPermissions.get(0)

        if (Permission.ACCESS_BACKGROUND_LOCATION == deniedPermission) {
            /*如果是申请后台定位权限 提示用户勾选始终允许*/
            message = getString(R.string.common_permission_background_location_fail_hint)
        }

        if (Permission.BODY_SENSORS_BACKGROUND == deniedPermission) {
            /*传感器权限只能选择始终允许*/
            message = getString(R.string.common_permission_background_sensors_fail_hint)
        }
    }
    return message
}