package com.yzq.kotlincommon.view_model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.*
import com.yzq.base.view_model.BaseViewModel
import com.yzq.common.constants.StoragePath
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.coroutine.scope.launchSafety
import com.yzq.coroutine.withIO
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

/**
 * Created by yzq on 2018/1/25.
 * 图片压缩model
 */

class CompressImgViewModel : BaseViewModel() {

    private val quality = 85
    private val textColor = Color.GRAY
    private val timeWaterMark = "YZQ:" + TimeUtils.getNowString()
    private val rootImgName = AppUtils.getAppPackageName() + "_"

    private val _compressedLiveData by lazy { MutableLiveData<String>() }

    val compressedImgPathLiveData: LiveData<String> = _compressedLiveData

    fun compressImg(
        path: String,
    ) {

        viewModelScope.launchSafety {
            val compressImagePath = doCompress(path)
            _compressedLiveData.value = compressImagePath

            /*上传图片接口示例*/

            val imageFile = File(compressImagePath)
            LogUtils.i("imageFile:${imageFile.path}")
            LogUtils.i("imageFile:${imageFile.name}")
            val multipartBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key1", "value1")
                .addFormDataPart("key2", "false")
                .addFormDataPart("key3", "1")
                .addFormDataPart("image",
                    imageFile.getName(),
                    imageFile.asRequestBody("image/*".toMediaTypeOrNull()))
                .build()


            val uploadImgResp = RetrofitFactory.instance.getService(ApiService::class.java)
                .uploadImg(multipartBody)
            LogUtils.i("uploadImgResp = ${uploadImgResp}")

        }
    }

    /*只压缩图片*/
    private suspend fun doCompress(path: String): String = withIO {
        LogUtils.i("path:$path")
        LogUtils.i("压缩前图片大小：" + FileUtils.getFileLength(path))
        /*获取图片旋转的角度*/
        val degree = readPictureDegree(path)
        var selectBitMap = ImageUtils.getBitmap(path)

        if (degree != 0) {
            LogUtils.i("图片旋转了，进行调整")
            val matrix = Matrix()
            matrix.postRotate(degree.toFloat())
            selectBitMap =
                Bitmap.createBitmap(
                    selectBitMap,
                    0,
                    0,
                    selectBitMap.width,
                    selectBitMap.height,
                    matrix,
                    true
                )
        }

        val defaultW = selectBitMap.width
        val defaultH = selectBitMap.height
        LogUtils.i("原图分辨率：$defaultW:$defaultH")
        val ratio = getRatioSize(defaultW, defaultH)
        LogUtils.i("缩放比例$ratio")
        /*先按比例压缩*/
        selectBitMap =
            ImageUtils.compressByScale(
                selectBitMap,
                (defaultW / ratio).toInt(),
                (defaultH / ratio).toInt()
            )
        LogUtils.i("比例压缩后：" + selectBitMap.width + ":" + selectBitMap.height)

        val imgArr = ImageUtils.compressByQuality(selectBitMap, quality)
        /*再按质量压缩*/
        selectBitMap = BitmapFactory.decodeByteArray(imgArr, 0, imgArr.size)

        LogUtils.i("质量压缩后的" + selectBitMap.width + ":" + selectBitMap.height)

        /*保存的文件名称*/
        val savedImgPath =
            StoragePath.externalAppPicturesPath + rootImgName + System.currentTimeMillis() + ".jpg"
        /*保存并返回图片路径*/
        if (ImageUtils.save(selectBitMap, savedImgPath, Bitmap.CompressFormat.JPEG, true)) {
            /*返回保存后的路径*/
            LogUtils.i("压缩后图片大小：" + FileUtils.getFileLength(savedImgPath))
            savedImgPath
        } else {
            /*返回原路径*/
            LogUtils.i("压缩失败，返回原图")
            path
        }
    }

    /*图片的旋转角度*/
    private fun readPictureDegree(path: String): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path)
            val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 ->
                    degree = 90

                ExifInterface.ORIENTATION_ROTATE_180 ->
                    degree = 180

                ExifInterface.ORIENTATION_ROTATE_270 ->
                    degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        LogUtils.i("图片旋转了：${degree}度")

        return degree
    }

    /*计算缩放比例*/
    private fun getRatioSize(bitWidth: Int, bitHeight: Int): Double {

        val maxSize = 1024.0

        var ratio = 1.0
        if (bitWidth > bitHeight && bitWidth > maxSize) {
            ratio = bitWidth / maxSize
        } else if (bitWidth < bitHeight && bitHeight > maxSize) {
            ratio = bitHeight / maxSize
        }
        if (ratio <= 0) {
            ratio = 1.0
        }
        return ratio
    }
}
