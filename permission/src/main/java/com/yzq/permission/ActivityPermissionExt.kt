package com.yzq.permission

import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.yzq.materialdialog.showPositiveCallbackDialog

fun AppCompatActivity.getPermissions(
    vararg permissions: String,
    permissionGranted: PermissionGranted,
) {
    PermissionUtils.permission(*permissions)
        .rationale { activity, shouldRequest ->
            /*用户拒绝后再次请求获取权限*/
            shouldRequest.again(true)
        }.callback(object : PermissionUtils.FullCallback {
            override fun onGranted(granted: MutableList<String>) {
                /*同意*/
                LogUtils.i("有权限$granted")
                permissionGranted(granted)
            }

            override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {
                /*拒绝 提示去设置手动打开权限*/
                LogUtils.i("权限被拒绝 deniedForever:$deniedForever,denied:$denied")


                if (deniedForever.size > 0 || denied.size > 0) {
                    /*存在被拒绝且不再提示的权限 此时需要提示用户打开设置手动开启权限*/
                    showPositiveCallbackDialog(
                        title = "开启权限",
                        message = "我们需要的权限被您拒绝，请手动开启权限，",
                        positiveText = "去开启",
                        negativeText = "不开启"
                    ) {
                        PermissionUtils.launchAppDetailsSettings()
                    }
                }
            }
        }).request()
}
