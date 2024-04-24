package com.yzq.base.extend

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import com.yzq.application.AppContext


/**
 * @description: 获取App的版本号
 * @return String
 */
fun getAppVersionName(): String {
    return AppContext.packageManager.getPackageInfo(AppContext.packageName, 0).versionName
}

/**
 * @description: 获取App的版本号
 * @return Int
 */
fun getAppVersionCode(): Long {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        AppContext.packageManager.getPackageInfo(AppContext.packageName, 0).longVersionCode
    } else {
        AppContext.packageManager.getPackageInfo(AppContext.packageName, 0).versionCode.toLong()
    }
}

@SuppressLint("WrongConstant")
fun copyText(text: CharSequence) {
    val clipboardManager =
        AppContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("text", text)
    clipboardManager.setPrimaryClip(clipData)
}