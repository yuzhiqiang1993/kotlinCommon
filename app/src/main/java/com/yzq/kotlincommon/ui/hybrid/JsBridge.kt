package com.yzq.kotlincommon.ui.hybrid

import android.webkit.JavascriptInterface
import com.yzq.logger.LogCat

/**
 * @description: 提供给webview调用的方法
 * @author : XeonYu
 * @date : 2020/8/6
 * @time : 11:10
 */

object JsBridge {

    @JavascriptInterface
    fun nativeFun() {
        LogCat.i("webView调本地方法")
    }
}
