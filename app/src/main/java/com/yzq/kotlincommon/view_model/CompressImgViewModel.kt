package com.yzq.kotlincommon.view_model

import ando.file.compressor.ImageCompressEngine
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yzq.application.AppManager
import com.yzq.application.AppStorage
import com.yzq.base.extend.save
import com.yzq.base.view_model.BaseViewModel
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.coroutine.safety_coroutine.launchSafety
import com.yzq.logger.Logger
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * Created by yzq on 2018/1/25.
 * 图片压缩model
 */

class CompressImgViewModel : BaseViewModel() {


    private val rootImgName = AppManager.getPackageName() + "_"

    private val _compressedLiveData by lazy { MutableLiveData<String>() }

    val compressedImgPathLiveData: LiveData<String> = _compressedLiveData

    fun compressImg(
        uri: Uri,
    ) {
        Logger.i("compressImg path:$uri")
        viewModelScope.launchSafety {

            ImageCompressEngine.compressPure(uri)?.let {
                Logger.i("compressPure path:$it")
                /*保存的文件名称*/
                val savedImgPath =
                    "${AppStorage.External.Private.picturesPath}$rootImgName${System.currentTimeMillis()}.jpg"
                val save = it.save(savedImgPath, Bitmap.CompressFormat.JPEG, 100, true)
                if (save) {
                    _compressedLiveData.value = savedImgPath
//                    uploadImg(savedImgPath)
                }
            }


        }
    }

    private suspend fun uploadImg(savedImgPath: String) {

        /*上传图片接口示例*/
        val imageFile = File(savedImgPath)
        Logger.i("imageFile:${imageFile.path}")
        Logger.i("imageFile:${imageFile.name}")
        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("key1", "front")
            .addFormDataPart("key2", "false")
            .addFormDataPart("key3", "1")
            .addFormDataPart(
                "image",
                imageFile.name,
                imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            )
            .build()


        val uploadImgResp = RetrofitFactory.instance.getService(ApiService::class.java)
            .uploadImg(multipartBody)
        Logger.i("uploadImgResp = ${uploadImgResp}")

    }

}
