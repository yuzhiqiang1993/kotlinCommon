package com.yzq.common.img

import com.qingmei2.rximagepicker.core.RxImagePicker
import com.qingmei2.rximagepicker.entity.Result
import com.yzq.common.ui.BaseActivity
import com.yzq.common.ui.BaseFragment
import io.reactivex.Observable


/**
 * @description: 图片选择器封装
 * @author : yzq
 * @date   : 2018/7/20
 * @time   : 11:27
 *
 */

object ImagePicker {

    /*打开相机*/
    fun openCamera(activity: BaseActivity): Observable<Result> {
        return RxImagePicker.Builder()
                .with(activity)
                .build()
                .create()
                .openCamera()

    }

    /*打开相机*/
    fun openCamera(fragment: BaseFragment): Observable<Result> {
        return RxImagePicker.Builder()
                .with(fragment)
                .build()
                .create()
                .openCamera()
    }


    /*打开图库，选单张*/
    fun openGallery(fragment: BaseFragment): Observable<Result> {
        return RxImagePicker.Builder()
                .with(fragment)
                .build()
                .create()
                .openGallery()
    }

    /*打开图库,选单张*/
    fun openGallery(activity: BaseActivity): Observable<Result> {
        return RxImagePicker.Builder()
                .with(activity)
                .build()
                .create()
                .openGallery()
    }

}