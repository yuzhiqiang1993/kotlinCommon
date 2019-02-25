package com.yzq.common.permission

import android.annotation.SuppressLint
import android.text.TextUtils
import com.blankj.utilcode.util.ToastUtils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import com.yzq.common.AppContext
import com.yzq.common.ui.BaseActivity
import com.yzq.common.widget.Dialog
import io.reactivex.Observable
import io.reactivex.ObservableEmitter


/**
 * @description: 封装的权限请求
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 17:32
 *
 */

object PermissionRequester {

    val REQUEST_CODE_SETTING = 1


    /*申请权限*/
    fun request(vararg permissions: String, activity: BaseActivity): Observable<Boolean> {

        return Observable.create<Boolean> { emitter: ObservableEmitter<Boolean> ->
            AndPermission.with(activity)
                    .runtime()
                    .permission(permissions)
                    .onGranted {
                        emitter.onNext(true)
                        emitter.onComplete()
                    }
                    .onDenied {
                        permissionDenied(it, activity)
                    }.start()
        }


    }

    /*权限被拒绝*/
    private fun permissionDenied(permissions: List<String>, activity: BaseActivity) {

        if (AndPermission.hasAlwaysDeniedPermission(AppContext, permissions)) {
            showPermissionDailog(permissions, activity)
        } else {
            ToastUtils.showShort("权限被拒绝")
        }


    }

    /*当用户点击拒绝且不再提示时显示提示框*/
    @SuppressLint("CheckResult")
    private fun showPermissionDailog(permissions: List<String>, activity: BaseActivity) {

        val permissionNames = Permission.transformText(AppContext, permissions)
        val message = "我们需要的 ${TextUtils.join("、", permissionNames)} 权限被拒绝,这将导致部分功能不可用，请手动开启! "


        Dialog.showPositiveCallbackDialog(title = "开启权限", message = message, positiveText = "去开启", negativeText = "不开启")
                .subscribe {

                    AndPermission.with(activity)
                            .runtime()
                            .setting()
                            .start(REQUEST_CODE_SETTING)

                }


    }

}

