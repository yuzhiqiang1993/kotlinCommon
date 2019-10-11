package com.yzq.lib_img

import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.UriUtils
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.yanzhenjie.permission.runtime.Permission
import com.yzq.lib_permission.requestPermissions
import java.io.File


typealias ImagePickerListener = (File) -> Unit

/*调相机拍照*/
fun AppCompatActivity.openCamera(imagePickerListener: ImagePickerListener) {


    requestPermissions(Permission.CAMERA) {
        RxImagePicker.create().openCamera(this)
            .subscribe { result ->
                val file = UriUtils.uri2File(result.uri)
                imagePickerListener(file)
            }


    }
}
