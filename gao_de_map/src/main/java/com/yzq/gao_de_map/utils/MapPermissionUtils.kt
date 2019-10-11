package com.yzq.gao_de_map.utils

import android.annotation.SuppressLint
import android.widget.Toast
import com.yanzhenjie.permission.Action
import com.yanzhenjie.permission.runtime.Permission
import com.yzq.common.utils.LocationUtils
import com.yzq.lib_base.ui.BaseActivity
import com.yzq.lib_permission.requestPermission
import io.reactivex.Single


/**
 * @description: 定位权限
 * @author : yzq
 * @date   : 2018/12/19
 * @time   : 16:04
 *
 */

object MapPermissionUtils {


    /*检查定位相关权限*/
    @SuppressLint("CheckResult")
    fun checkLocationPermission(needGps: Boolean = false, activity: BaseActivity): Single<Boolean> {

        return Single.create { emitter ->
            activity.requestPermission(
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.READ_EXTERNAL_STORAGE,
                Permission.READ_PHONE_STATE,
                granted = Action {
                    if (needGps) {

                        if (LocationUtils.isGpsEnabled()) {
                            emitter.onSuccess(true)
                        } else {
                            Toast.makeText(
                                activity,
                                "该功能需要获取当前位置信息，请打开GPS",
                                Toast.LENGTH_LONG
                            ).show()
                            LocationUtils.openGpsSettings()
                        }

                    } else {
                        emitter.onSuccess(true)
                    }

                }

            )


        }


    }


}