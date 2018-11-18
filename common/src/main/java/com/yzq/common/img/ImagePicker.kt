package com.yzq.common.img

import android.content.Context
import android.provider.MediaStore
import com.blankj.utilcode.util.UriUtils
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.yanzhenjie.permission.Permission
import com.yzq.common.permission.PermissionRequester
import io.reactivex.Observable
import java.io.File


/**
 * @description: 拍照和选图
 * @author : yzq
 * @date   : 2018/11/18
 * @time   : 17:25
 *
 */

object ImagePicker {


    /*拍照*/
    fun openCamera(context: Context): Observable<File> {

        return Observable.create<File> { emitter ->

            PermissionRequester.request(Permission.CAMERA)
                    .subscribe {
                        RxImagePicker.create().openCamera(context)
                                .subscribe {
                                    val file = UriUtils.uri2File(it.uri, MediaStore.Images.Media.DATA)
                                    emitter.onNext(file)
                                    emitter.onComplete()
                                }

                    }

        }

    }


    /*选择照片*/
    fun openGallery(context: Context): Observable<File> {

        return Observable.create<File> { emitter ->

            PermissionRequester.request(*Permission.Group.STORAGE)
                    .subscribe {
                        RxImagePicker.create().openGallery(context)
                                .subscribe {
                                    val file = UriUtils.uri2File(it.uri, MediaStore.Images.Media.DATA)
                                    emitter.onNext(file)
                                    emitter.onComplete()
                                }

                    }

        }

    }

}