package com.yzq.gao_de_map.utils

import android.annotation.SuppressLint
import android.widget.Toast
import com.yanzhenjie.permission.Permission
import com.yzq.common.AppContext
import com.yzq.common.permission.PermissionRequester
import com.yzq.common.utils.LocationUtils
import io.reactivex.Observable


/**
 * @description: 定位权限
 * @author : yzq
 * @date   : 2018/12/19
 * @time   : 16:04
 *
 */

class MapPermissionUtils {

    companion object {

        /*检查定位相关权限*/
        @SuppressLint("CheckResult")
        fun checkLocationPermission(needGps: Boolean = false): Observable<Boolean> {

            return Observable.create<Boolean> { emitter ->
                PermissionRequester.request(
                        Permission.ACCESS_FINE_LOCATION
                        , Permission.ACCESS_COARSE_LOCATION
                        , Permission.WRITE_EXTERNAL_STORAGE
                        , Permission.READ_EXTERNAL_STORAGE
                        , Permission.READ_PHONE_STATE
                ).subscribe {

                    if (needGps) {

                        if (LocationUtils.isGpsEnabled()) {
                            emitter.onNext(true)
                        } else {
                            Toast.makeText(AppContext, "该功能需要获取当前位置信息，请打开GPS", Toast.LENGTH_LONG).show()
                            LocationUtils.openGpsSettings()
                        }

                    } else {
                        emitter.onNext(true)
                    }

                    emitter.onComplete()


                }


            }


        }


    }


}