package com.yzq.common.permission

import android.annotation.SuppressLint
import android.text.TextUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import com.yanzhenjie.permission.Setting
import com.yzq.common.AppContext
import com.yzq.common.BaseApp
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

    /*申请权限*/
    fun request(vararg permissions: String): Observable<Boolean> {

        return Observable.create<Boolean> { emitter: ObservableEmitter<Boolean> ->
            AndPermission.with(AppContext)
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

    /*权限被拒绝*/
    private fun permissionDenied(permissions: List<String>) {

        if (AndPermission.hasAlwaysDeniedPermission(AppContext, permissions)) {
            showPermissionDailog(permissions)
        } else {
            ToastUtils.showShort("权限被拒绝")
        }


    }

    /*当用户点击拒绝且不再提示时显示提示框*/
    @SuppressLint("CheckResult")
    private fun showPermissionDailog(permissions: List<String>) {

        val permissionNames = Permission.transformText(AppContext, permissions)
        val message = "我们需要的 " + TextUtils.join("、", permissionNames) + " 权限被拒绝,这将导致部分功能不可用，请手动开启!"


        Dialog.showPositiveCallbackDialog(title = "开启权限", content = message, positiveText = "去开启", negativeText = "不开启")
                .subscribe {
                    AndPermission.with(AppContext)
                            .runtime()
                            .setting()
                            .onComeback {

                                LogUtils.i("从设置回来了")
                            }.start()

                }


    }

}

