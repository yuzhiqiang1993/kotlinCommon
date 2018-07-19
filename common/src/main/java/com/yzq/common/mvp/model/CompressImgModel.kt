package com.yzq.common.mvp.model

import android.graphics.Bitmap
import android.graphics.Color
import android.text.TextUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.yzq.common.constants.ProjectPath
import io.reactivex.Observable
import javax.inject.Inject


/**
 * @description: 图片压缩model
 * @author : yzq
 * @date   : 2018/7/19
 * @time   : 15:37
 *
 */

class CompressImgModel @Inject constructor() {

    private val maxWidth = 600
    private val maxHeight = 800
    private val quality = 85
    private val textSize = 25
    private val textColor = Color.BLACK
    private val offsetX = 20
    private val offsetY = 40
    private val defaultWaterMark = "ESP:" + TimeUtils.getNowString()
    private val rootImgName = "_"


    /*压缩图片保存路径*/
    fun compressImgWithWatermark(path: String,  waterMark: String=defaultWaterMark): Observable<String> {
        return Observable.create { e ->
            LogUtils.i("压缩前图片大小：" + FileUtils.getFileSize(path))
            val selectBitMap = ImageUtils.getBitmap(path, maxWidth, maxHeight)

            /*添加水印*/
            val watermarkImg = ImageUtils.addTextWatermark(selectBitMap, waterMark, textSize, textColor, offsetX.toFloat(), (selectBitMap.height - offsetY).toFloat())
            /*压缩*/
            val compressedImg = ImageUtils.compressByQuality(watermarkImg, quality, true)
            /*保存的文件名称*/
            val savedImgPath = "${ProjectPath.PICTURE_PATH}${rootImgName}${System.currentTimeMillis()}.jpg"
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

    /*压缩图片*/
    fun compressImg(path: String, addWaterMark: Boolean): Observable<String> {

        return Observable.create { e ->
            var selectBitMap = ImageUtils.getBitmap(path, maxWidth, maxHeight)

            /*添加水印*/
            if (addWaterMark) {
                selectBitMap = ImageUtils.addTextWatermark(selectBitMap, defaultWaterMark, textSize, textColor, offsetX.toFloat(), (selectBitMap.height - offsetY).toFloat())
            }

            /*压缩*/
            val compressedImg = ImageUtils.compressByQuality(selectBitMap, quality, true)
            /*保存的文件名称*/
            val savedImgPath = "${ProjectPath.PICTURE_PATH}${rootImgName}${System.currentTimeMillis()}.jpg"
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


    /*只添加水印*/
    fun addWaterMark(path: String, waterMark: String = defaultWaterMark): Observable<String> {

        return Observable.create { e ->
            val selectBitMap = ImageUtils.getBitmap(path, maxWidth, maxHeight)

            /*添加时间水印*/
            val watermarkImg = ImageUtils.addTextWatermark(selectBitMap, waterMark, textSize, textColor, offsetX.toFloat(), (selectBitMap.height - offsetY).toFloat())
            /*保存的文件名称*/
            val savedImgPath = "${ProjectPath.PICTURE_PATH}${rootImgName}${System.currentTimeMillis()}.jpg"
            /*保存并返回图片路径*/
            if (ImageUtils.save(watermarkImg, savedImgPath, Bitmap.CompressFormat.JPEG)) {
                /*返回保存后的路径*/
                e.onNext(savedImgPath)

            } else {
                /*返回原路径*/
                e.onNext(path)
            }
            e.onComplete()
        }


    }


}