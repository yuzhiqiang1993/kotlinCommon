package com.yzq.common.base.mvp.model

import android.graphics.Bitmap
import android.graphics.Color
import com.blankj.utilcode.util.*
import com.yzq.common.R
import com.yzq.common.constants.ProjectPath
import io.reactivex.Observable
import javax.inject.Inject


/**
 * Created by yzq on 2018/1/25.
 * 图片压缩model
 */

class CompressImgModel @Inject constructor() {

    private val quality = 85
    private val textColor = Color.GRAY
    private val waterMark = "ESP:" + TimeUtils.getNowString()
    internal val rootImgName = AppUtils.getAppPackageName() + "_"


    /*压缩图片保存路径*/
    fun compressImgWithWatermark(path: String): Observable<String> {
        return Observable.create { e ->
            LogUtils.i("压缩前图片大小：" + FileUtils.getFileSize(path))

            var selectBitMap = ImageUtils.getBitmap(path)

            LogUtils.i("密度：" + selectBitMap.density)

            val defaultW = selectBitMap.width
            val defaultH = selectBitMap.height

            LogUtils.i("原图分辨率：$defaultW:$defaultH")

            val textSize = defaultW / selectBitMap.density * 14

            /*添加图片水印*/
            val watermarkLogo = ImageUtils.getBitmap(R.drawable.water_logo)

            val logoW = watermarkLogo.getWidth()
            val logoH = watermarkLogo.getHeight()
            var logoRatio = 1.0
            if (logoW > defaultW * 0.6) {
                logoRatio = logoW / (defaultW * 0.6)
                LogUtils.i("logoRatio$logoRatio")
            }

            val logo = ImageUtils.compressByScale(watermarkLogo, (logoW / logoRatio).toInt(), (logoH / logoRatio).toInt(), true)


            val offsetX = defaultW / 2 - logo.getWidth() / 2
            val offsetY = defaultH / 2 - logo.getHeight() / 2


            /*添加时间文字水印*/
            selectBitMap = ImageUtils.addTextWatermark(selectBitMap, waterMark, textSize, textColor, offsetX.toFloat(), (defaultH - textSize - offsetY).toFloat())

            val watermarkBitmap = ImageUtils.addImageWatermark(selectBitMap, logo, offsetX, offsetY - textSize, 100, true)

            val ratio = getRatioSize(defaultW, defaultH)
            LogUtils.i("缩放比例$ratio")
            /*先按比例压缩*/
            val scaleBitmap = ImageUtils.compressByScale(watermarkBitmap, (defaultW / ratio).toInt(), (defaultH / ratio).toInt(), true)
            LogUtils.i("比例压缩后：" + scaleBitmap.width + ":" + scaleBitmap.height)

            /*再按质量压缩*/
            val compressedImg = ImageUtils.compressByQuality(scaleBitmap, quality)

            LogUtils.i("压缩后的" + compressedImg.width + ":" + compressedImg.height)

            /*保存的文件名称*/
            val savedImgPath = ProjectPath.PICTURE_PATH + rootImgName + System.currentTimeMillis() + ".jpg"
            /*保存并返回图片路径*/
            if (ImageUtils.save(compressedImg, savedImgPath, Bitmap.CompressFormat.JPEG)) {
                /*返回保存后的路径*/
                e.onNext(savedImgPath)
                LogUtils.i("压缩后图片大小：" + FileUtils.getFileSize(savedImgPath))
            } else {
                /*返回原路径*/
                e.onNext(path)
            }
            e.onComplete()
        }
    }


    /*只压缩图片*/
    fun compressImg(path: String): Observable<String> {

        return Observable.create { e ->
            LogUtils.i("压缩前图片大小：" + FileUtils.getFileSize(path))
            val selectBitMap = ImageUtils.getBitmap(path)

            val defaultW = selectBitMap.width
            val defaultH = selectBitMap.height
            LogUtils.i("原图分辨率：$defaultW:$defaultH")
            val ratio = getRatioSize(defaultW, defaultH)
            LogUtils.i("缩放比例$ratio")
            /*先按比例压缩*/
            val scaleBitmap = ImageUtils.compressByScale(selectBitMap, (defaultW / ratio).toInt(), (defaultH / ratio).toInt(), true)
            LogUtils.i("比例压缩后：" + scaleBitmap.width + ":" + scaleBitmap.height)

            /*再按质量压缩*/
            val compressedImg = ImageUtils.compressByQuality(scaleBitmap, quality)

            LogUtils.i("质量压缩后的" + compressedImg.width + ":" + compressedImg.height)

            /*保存的文件名称*/
            val savedImgPath = ProjectPath.PICTURE_PATH + rootImgName + System.currentTimeMillis() + ".jpg"
            /*保存并返回图片路径*/
            if (ImageUtils.save(compressedImg, savedImgPath, Bitmap.CompressFormat.JPEG)) {
                /*返回保存后的路径*/
                e.onNext(savedImgPath)
                LogUtils.i("压缩后图片大小：" + FileUtils.getFileSize(savedImgPath))
            } else {
                /*返回原路径*/
                e.onNext(path)
                LogUtils.i("压缩失败，返回原图")
            }
            e.onComplete()
        }

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
