package com.yzq.permission

import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.OnPermissionPageCallback
import com.hjq.permissions.XXPermissions
import com.yzq.materialdialog.showPositiveCallbackDialog


/**
 * @description 快速获取权限
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/10
 * @time    17:06
 */

fun AppCompatActivity.getPermissions(
    vararg permissions: String,
    permissionGranted: () -> Unit,
) {
    /*如果已经有权限，直接执行逻辑*/
    if (XXPermissions.isGranted(this, permissions)) {
        permissionGranted.invoke()
        return
    }

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

            override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                if (doNotAskAgain) {
                    /*用户点了拒绝并勾选了不在提示 弹窗提示用户手动给权限*/
                    showPermissionSettingDialog(
                        permissions,
                        permissionGranted)
                }
            }

        })

}

fun AppCompatActivity.showPermissionSettingDialog(
    permissions: MutableList<String>,
    permissionGranted: () -> Unit,
) {

    showPositiveCallbackDialog("授权提醒",
        "${PermissionNameConvert.getPermissionString(this, permissions)} 获取失败，请手动赋予") {
        /*跳转到设置页面*/
        XXPermissions.startPermissionActivity(this,
            permissions,
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