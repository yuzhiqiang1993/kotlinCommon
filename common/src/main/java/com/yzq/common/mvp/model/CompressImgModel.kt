package com.yzq.common.mvp.model

import android.graphics.Bitmap
import android.graphics.Color
import android.text.TextUtils
import com.blankj.utilcode.util.*
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

    /*最大宽度*/
    private val maxWidth = 600
    /*最大高度*/
    private val maxHeight = 800
    /*压缩质量*/
    private val quality = 85

    private val textSizeDp = 10f
    /*文字大小*/
    private val textSize = SizeUtils.dp2px(textSizeDp)
    /*颜色*/
    private val textColor = Color.BLACK
    /*x轴偏移量*/
    private val offsetX = SizeUtils.dp2px(5f).toFloat()
    /*y轴偏移量*/
    private val offsetY = SizeUtils.dp2px(textSizeDp + 5).toFloat()
    /*默认水印*/
    private var defaultWaterMark = "ESP:" + TimeUtils.getNowString()
    private val rootImgName = "_"


    /*压缩图片保存路径*/
    fun compressImgWithWatermark(path: String, waterMark: String): Observable<String> {


        return Observable.create { e ->
            LogUtils.i("压缩前图片大小：" + FileUtils.getFileSize(path))
            val selectBitMap = ImageUtils.getBitmap(path, maxWidth, maxHeight)

            if (!TextUtils.isEmpty(waterMark)) {
                defaultWaterMark = waterMark
            }

            /*添加水印*/
            val watermarkImg = ImageUtils.addTextWatermark(selectBitMap, defaultWaterMark, textSize, textColor, offsetX, selectBitMap.height - offsetY)
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

        }
    }

    /*压缩图片*/
    fun compressImg(path: String, addWaterMark: Boolean): Observable<String> {

        return Observable.create { e ->
            var selectBitMap = ImageUtils.getBitmap(path, maxWidth, maxHeight)

            /*添加水印*/
            if (addWaterMark) {
                selectBitMap = ImageUtils.addTextWatermark(selectBitMap, defaultWaterMark, textSize, textColor, offsetX, selectBitMap.height - offsetY)
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

        }

    }


    /*只添加水印*/
    fun addWaterMark(path: String, waterMark: String = defaultWaterMark): Observable<String> {

        return Observable.create { e ->
            val selectBitMap = ImageUtils.getBitmap(path, maxWidth, maxHeight)

            /*添加时间水印*/
            val watermarkImg = ImageUtils.addTextWatermark(selectBitMap, waterMark, textSize, textColor, offsetX, selectBitMap.height - offsetY)
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

        }


    }


}