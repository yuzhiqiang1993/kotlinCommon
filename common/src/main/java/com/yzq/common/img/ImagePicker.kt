package com.yzq.common.img

import com.blankj.utilcode.util.UriUtils
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.yanzhenjie.permission.runtime.Permission
import com.yzq.common.permission.PermissionRequester
import com.yzq.common.ui.BaseActivity
import io.reactivex.Single
import java.io.File


/**
 * @description: 拍照和选图
 * @author : yzq
 * @date   : 2018/11/18
 * @time   : 17:25
 *
 */

object ImagePicker {


    /**
     * 调相机拍照
     *
     * @param activity
     */
    fun openCamera(activity: BaseActivity): Single<File> {

        return Single.create { emitter ->

            PermissionRequester.request(Permission.CAMERA, activity = activity)
                    .subscribe { hasPermission ->
                        RxImagePicker.create().openCamera(activity)
                                .subscribe {
                                    val file = UriUtils.uri2File(it.uri)
                                    emitter.onSuccess(file)
                                }

                    }

        }

    }


    /**
     * 从图库选择
     *
     * @param activity
     */
    fun openGallery(activity: BaseActivity): Single<File> {

        return Single.create { emitter ->

            PermissionRequester.request(*Permission.Group.STORAGE, activity = activity)
                    .subscribe { hasPermission ->
                        RxImagePicker.create().openGallery(activity)
                                .subscribe {
                                    val file = UriUtils.uri2File(it.uri)
                                    emitter.onSuccess(file)

                                }

                    }

        }

    }

}