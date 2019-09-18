package com.yzq.common.view_model

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.*
import com.yzq.common.R
import com.yzq.common.constants.StoragePath
import com.yzq.lib_base.view_model.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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


    var compressedImgPath = MutableLiveData<String>()


    fun compressImg(
        path: String,
        needWatermark: Boolean = false,
        waterMarkArr: ArrayList<String> = arrayListOf(timeWaterMark)
    ) {

        viewModelScope.launch {

            compressedImgPath.value = withContext(Dispatchers.IO) {

                if (needWatermark) {
                    compressImgWithWatermark(path, waterMarkArr)
                } else {
                    compressImg(path)
                }

            }


        }


    }


    /*压缩图片保存路径*/
    private fun compressImgWithWatermark(
        path: String,
        waterMarkArr: ArrayList<String>
    ): String {


        LogUtils.i("压缩前图片大小：" + FileUtils.getFileSize(path))


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


        LogUtils.i("密度：" + selectBitMap.density)

        val defaultW = selectBitMap.width
        val defaultH = selectBitMap.height

        LogUtils.i("原图分辨率：$defaultW:$defaultH")

        val textSize = defaultW / selectBitMap.density * 14

        /*添加图片水印*/
        var watermarkLogo = ImageUtils.getBitmap(R.drawable.ic_launcher)

        val logoW = watermarkLogo.width
        val logoH = watermarkLogo.height
        var logoRatio = 1.0
        if (logoW > defaultW * 0.6) {
            logoRatio = logoW / (defaultW * 0.6)
            LogUtils.i("logoRatio$logoRatio")
        }

        watermarkLogo = ImageUtils.compressByScale(
            watermarkLogo,
            (logoW / logoRatio).toInt(),
            (logoH / logoRatio).toInt()
        )


        val offsetX = defaultW / 2 - watermarkLogo.width / 2
        val offsetY = defaultH / 2 - watermarkLogo.height / 2

        LogUtils.i("offsetY=${offsetY}")


        selectBitMap =
            ImageUtils.addImageWatermark(
                selectBitMap,
                watermarkLogo,
                offsetX,
                offsetY - textSize,
                100
            )

        /*添加文字水印*/

        for ((index, str) in waterMarkArr.withIndex()) {

            val strOffsetY =
                (textSize * (index + 1) + offsetY + watermarkLogo.height / 2).toFloat()

            LogUtils.i("strOffsetY:${strOffsetY}")

            selectBitMap = ImageUtils.addTextWatermark(
                selectBitMap,
                str,
                textSize,
                textColor,
                offsetX.toFloat(),
                strOffsetY
            )
        }


        val ratio = getRatioSize(defaultW, defaultH)
        LogUtils.i("缩放比例$ratio")
        /*先按比例压缩*/
        selectBitMap = ImageUtils.compressByScale(
            selectBitMap,
            (defaultW / ratio).toInt(),
            (defaultH / ratio).toInt()
        )
        LogUtils.i("比例压缩后：" + selectBitMap.width + ":" + selectBitMap.height)

        /*再按质量压缩*/
        selectBitMap = ImageUtils.compressByQuality(selectBitMap, quality)

        LogUtils.i("压缩后的" + selectBitMap.width + ":" + selectBitMap.height)

        /*保存的文件名称*/
        val savedImgPath =
            StoragePath.externalAppPicturesPath + rootImgName + System.currentTimeMillis() + ".jpg"


        LogUtils.i("图片存储路径：${savedImgPath}")
        /*保存并返回图片路径*/
        if (ImageUtils.save(selectBitMap, savedImgPath, Bitmap.CompressFormat.JPEG, true)) {
            /*返回保存后的路径*/


            LogUtils.i("压缩后图片大小：" + FileUtils.getFileSize(savedImgPath))
            return savedImgPath
        } else {
            /*返回原路径*/
            LogUtils.i("保存失败，返回原路径:${path}")
            return path

        }


    }


    /*只压缩图片*/
    private fun compressImg(path: String): String {


        LogUtils.i("压缩前图片大小：" + FileUtils.getFileSize(path))
        var selectBitMap = ImageUtils.getBitmap(path)

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

        /*再按质量压缩*/
        selectBitMap = ImageUtils.compressByQuality(selectBitMap, quality)

        LogUtils.i("质量压缩后的" + selectBitMap.width + ":" + selectBitMap.height)

        /*保存的文件名称*/
        val savedImgPath =
            StoragePath.externalAppPicturesPath + rootImgName + System.currentTimeMillis() + ".jpg"
        /*保存并返回图片路径*/
        if (ImageUtils.save(selectBitMap, savedImgPath, Bitmap.CompressFormat.JPEG, true)) {
            /*返回保存后的路径*/


            LogUtils.i("压缩后图片大小：" + FileUtils.getFileSize(savedImgPath))
            return savedImgPath
        } else {
            /*返回原路径*/

            LogUtils.i("压缩失败，返回原图")
            return path
        }


    }


    /*图片的旋转角度*/
    private fun readPictureDegree(path: String): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path);
            val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            );
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
