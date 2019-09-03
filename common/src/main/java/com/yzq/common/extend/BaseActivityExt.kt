package com.yzq.common.extend

import android.annotation.SuppressLint
import android.text.TextUtils
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UriUtils
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import com.ycuwq.datepicker.date.DatePicker
import com.ycuwq.datepicker.date.YearPicker
import com.ycuwq.datepicker.time.HourAndMinutePicker
import com.yzq.common.R
import com.yzq.common.ui.BaseActivity
import com.yzq.lib_base.AppContext
import com.yzq.lib_base.constants.BaseConstants
import com.yzq.lib_materialdialog.getNewDialog
import com.yzq.lib_materialdialog.showPositiveCallbackDialog
import io.reactivex.Single
import java.io.File


/*
* 对BaseActivity类的扩展
* */




/*权限申请*/
fun BaseActivity.requestPermission(vararg permissions: String): Single<Boolean> {
    return Single.create { singleEmitter ->

        AndPermission.with(this)
            .runtime()
            .permission(permissions)
            .onGranted {
                singleEmitter.onSuccess(true)
            }.onDenied {
                permissionDenied(it)
            }.start()


    }

}

/**
 * 权限被拒绝
 *
 * @param permissions  要申请的权限
 *
 */
private fun BaseActivity.permissionDenied(permissions: List<String>) {

    if (AndPermission.hasAlwaysDeniedPermission(AppContext, permissions)) {
        this.showPermissionDailog(permissions)
    } else {
        ToastUtils.showShort("权限被拒绝")
    }


}


private val REQUEST_CODE_SETTING = 1
/**
 * 用户拒绝权限后的提示框
 *
 * @param permissions  用户拒绝的权限
 */
@SuppressLint("CheckResult")
private fun BaseActivity.showPermissionDailog(permissions: List<String>) {

    val permissionNames = Permission.transformText(AppContext, permissions)
    val message = "我们需要的 ${TextUtils.join("、", permissionNames)} 权限被拒绝,这将导致部分功能不可用，请手动开启! "


    showPositiveCallbackDialog(
        title = "开启权限",
        message = message,
        positiveText = "去开启",
        negativeText = "不开启"
    )
        .subscribe { click ->
            AndPermission.with(this)
                .runtime()
                .setting()
                .start(REQUEST_CODE_SETTING)

        }


}


/*调相机拍照*/
fun BaseActivity.openCamera(): Single<File> {
    return Single.create { singleEmitter ->

        requestPermission(Permission.CAMERA)
            .subscribe { hasPermission ->
                RxImagePicker.create().openCamera(this)
                    .subscribe { result ->
                        val file = UriUtils.uri2File(result.uri)
                        singleEmitter.onSuccess(file)
                    }


            }
    }
}
