package com.yzq.base.extend

import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.yzq.application.AppContext
import com.yzq.application.AppManager
import com.yzq.logger.Logger
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.security.MessageDigest

/**
 * @description: 获取文件打开的intent
 * @author : XeonYu
 * @date : 2020/9/20
 * @time : 12:52
 */

fun File.getOpenIntent(): Intent {
    val intent = Intent("android.intent.action.VIEW")
    intent.addCategory("android.intent.category.DEFAULT")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    val uri: Uri = FileProvider.getUriForFile(
        AppContext.applicationContext,
        AppManager.getPackageName() + ".provider",
        this
    )

    val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(name)
    Logger.i("mimeType:$mimeType")
    intent.setDataAndType(uri, mimeType)

    return intent
}


/**
 * @description: 将输入流写入文件
 * @param filePath String
 * @param inputStream InputStream
 * @return Boolean
 */
const val DEFAULT_BUFFER_SIZE = 1024
fun writeFileFromIS(filePath: String, inputStream: InputStream): Boolean {
    return try {
        val file = File(filePath).apply {
            if (!exists()) {
                createNewFile()
            }
        }
        FileOutputStream(file).use { fos ->
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                fos.write(buffer, 0, len)
            }
            fos.flush()
        }
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    } finally {
        try {
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


/**
 * @description 获取文件的MD5值
 * @receiver File
 * @return String
 */
fun File.md5(): String {
    val digest = MessageDigest.getInstance("MD5")
    val fis = FileInputStream(this)
    val buffer = ByteArray(1024)
    var len: Int
    while (fis.read(buffer).also { len = it } != -1) {
        digest.update(buffer, 0, len)
    }
    fis.close()
    val bytes = digest.digest()
    val hexString = StringBuilder()
    for (b in bytes) {
        val hex = Integer.toHexString(0xFF and b.toInt())
        if (hex.length == 1) {
            hexString.append('0')
        }
        hexString.append(hex)
    }
    return hexString.toString()
}


/**
 * @description 获取文件的长度
 * @receiver String
 * @return Long
 */
fun String.getFileLength(): Long {
    return File(this).length()
}