package com.yzq.core.extend

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

/**
 * 按比例缩放图片
 * @receiver Bitmap
 * @param targetWidth Int
 * @param targetHeight Int
 * @return Bitmap
 */
fun Bitmap.compressByScale(targetWidth: Int, targetHeight: Int): Bitmap {
    val originalWidth = this.width
    val originalHeight = this.height

    val scaleWidth = targetWidth.toFloat() / originalWidth
    val scaleHeight = targetHeight.toFloat() / originalHeight
    val scale = if (scaleWidth < scaleHeight) scaleWidth else scaleHeight

    val matrix = android.graphics.Matrix().apply {
        postScale(scale, scale)
    }

    return Bitmap.createBitmap(this, 0, 0, originalWidth, originalHeight, matrix, true)
}


/**
 * 保存图片
 * @receiver Bitmap
 * @param filePath String
 * @param format Bitmap.CompressFormat
 * @param quality Int
 * @param recycle Boolean
 * @return Boolean
 */
fun Bitmap.save(
    filePath: String,
    format: Bitmap.CompressFormat,
    quality: Int,
    recycle: Boolean = true
): Boolean {
    val file = File(filePath)
    var fos: FileOutputStream? = null
    return try {
        fos = FileOutputStream(file)
        this.compress(format, quality, fos)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    } finally {
        try {
            fos?.flush()
            fos?.close()
            if (recycle && !this.isRecycled) {
                this.recycle()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
