package com.yzq.permission

import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.OnPermissionPageCallback
import com.hjq.permissions.XXPermissions
import com.yzq.materialdialog.showPositiveCallbackDialog

/**
 *
 * @receiver Fragment
 * @param permissions Array<out String>
 * @param permissionGranted Function1<List<String>, Unit>
 */
fun Fragment.getPermissions(
    vararg permissions: String,
    permissionGranted: () -> Unit,
) {
    /*如果已经有权限，直接执行逻辑*/
    if (XXPermissions.isGranted(requireActivity(), permissions)) {
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

fun Fragment.showPermissionSettingDialog(
    permissions: MutableList<String>,
    permissionGranted: () -> Unit,
) {

    showPositiveCallbackDialog("授权提醒",
        "${PermissionNameConvert.getPermissionString(requireActivity(), permissions)} 获取失败，请手动赋予") {
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