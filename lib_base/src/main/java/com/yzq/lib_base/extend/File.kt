package com.yzq.lib_base.extend

import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.LogUtils
import com.yzq.lib_application.AppContext
import java.io.File


/**
 * @description: 获取文件打开的intent
 * @author : XeonYu
 * @date   : 2020/9/20
 * @time   : 12:52
 */

fun File.getOpenIntent(): Intent {
    val intent = Intent("android.intent.action.VIEW")
    intent.addCategory("android.intent.category.DEFAULT")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    val uri: Uri = FileProvider.getUriForFile(
        AppContext.applicationContext,
        AppUtils.getAppPackageName() + ".provider",
        FileUtils.getFileByPath(path)
    )

    val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(name)
    LogUtils.i("mimeType:${mimeType}")
    intent.setDataAndType(uri, mimeType)

    return intent

}




