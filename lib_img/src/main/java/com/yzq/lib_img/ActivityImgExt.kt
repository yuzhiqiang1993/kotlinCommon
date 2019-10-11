package com.yzq.lib_img

import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.UriUtils
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.yanzhenjie.permission.Action
import com.yanzhenjie.permission.runtime.Permission
import com.yzq.lib_permission.requestPermission
import io.reactivex.Single
import java.io.File

/*调相机拍照*/
fun AppCompatActivity.openCamera(): Single<File> {
    return Single.create { singleEmitter ->

        requestPermission(
            Permission.CAMERA,
            granted = Action {
                RxImagePicker.create().openCamera(this)
                    .subscribe { result ->
                        val file = UriUtils.uri2File(result.uri)
                        singleEmitter.onSuccess(file)
                    }
            }
        )


    }
}
