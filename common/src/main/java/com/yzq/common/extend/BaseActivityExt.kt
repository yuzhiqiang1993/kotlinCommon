package com.yzq.common.extend

import android.annotation.SuppressLint
import android.text.TextUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UriUtils
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import com.yzq.common.AppContext
import com.yzq.common.ui.BaseActivity
import com.yzq.common.widget.Dialog
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import java.io.File


val REQUEST_CODE_SETTING = 1
/**
 * 申请权限
 * @receiver BaseActivity
 * @param permissions Array<out String>
 * @return Observable<Boolean>
 */
fun BaseActivity.requestPermission(vararg permissions: String): Observable<Boolean> {


    return Observable.create<Boolean> { emitter: ObservableEmitter<Boolean> ->
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .onGranted {
                    emitter.onNext(true)
                    emitter.onComplete()
                }
                .onDenied {
                    permissionDenied(it)
                }.start()
    }


}

private fun BaseActivity.permissionDenied(permissions: List<String>) {

    if (AndPermission.hasAlwaysDeniedPermission(AppContext, permissions)) {
        showPermissionDailog(permissions)
    } else {
        ToastUtils.showShort("权限被拒绝")
    }


}


@SuppressLint("CheckResult")
private fun BaseActivity.showPermissionDailog(permissions: List<String>) {

    val permissionNames = Permission.transformText(AppContext, permissions)
    val message = "我们需要的 ${TextUtils.join("、", permissionNames)} 权限被拒绝,这将导致部分功能不可用，请手动开启! "


    Dialog.showPositiveCallbackDialog(title = "开启权限", message = message, positiveText = "去开启", negativeText = "不开启")
            .subscribe {
                AndPermission.with(this)
                        .runtime()
                        .setting()
                        .start(REQUEST_CODE_SETTING)

            }


}


/**
 *打开相机
 * @receiver BaseActivity
 * @return Observable<File>
 */
fun BaseActivity.openCamera(): Observable<File> {
    return Observable.create<File> { emitter ->

        requestPermission(Permission.CAMERA)
                .subscribe {
                    RxImagePicker.create().openCamera(this)
                            .subscribe {
                                val file = UriUtils.uri2File(it.uri)
                                emitter.onNext(file)
                                emitter.onComplete()
                            }

                }

    }

}






