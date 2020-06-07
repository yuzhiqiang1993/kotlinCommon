package com.yzq.lib_permission

import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.AndPermission.hasAlwaysDeniedPermission
import com.yanzhenjie.permission.runtime.Permission.transformText
import com.yzq.lib_materialdialog.showPositiveCallbackDialog


/**
 *
 * @receiver AppCompatActivity
 * @param permissions Array<out String>  要申请的权限
 * @param permissionGranted Function1<List<String>, Unit>  权限允许回调函数
 */
fun AppCompatActivity.getPermissions(
    vararg permissions: String,
    permissionGranted: PermissionGranted
) {
    AndPermission.with(this)
        .runtime()
        .permission(permissions)
        .onGranted {
            permissionGranted(it)
        }
        .onDenied {
            permissionDenied(it)
        }.start()


}


/**
 * 权限被拒绝
 *
 * @param permissions  要申请的权限
 *
 */
private fun AppCompatActivity.permissionDenied(permissions: List<String>) {

    if (hasAlwaysDeniedPermission(this, permissions)) {
        showPermissionDailog(permissions)
    } else {
        ToastUtils.showShort("权限被拒绝")
    }


}


private const val REQUEST_CODE_SETTING = 1

/**
 * 用户拒绝权限后的提示框
 *
 * @param permissions  用户拒绝的权限
 */
private fun AppCompatActivity.showPermissionDailog(permissions: List<String>) {

    val permissionNames = transformText(this, permissions)
    val message =
        "我们需要的 ${android.text.TextUtils.join("、", permissionNames)} 权限被拒绝,这将导致部分功能不可用，请手动开启! "

    showPositiveCallbackDialog(
        title = "开启权限",
        message = message,
        positiveText = "去开启",
        negativeText = "不开启"
    ) {
        AndPermission.with(this)
            .runtime()
            .setting()
            .start(REQUEST_CODE_SETTING)

    }


}
