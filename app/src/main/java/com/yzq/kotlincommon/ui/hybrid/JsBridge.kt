package com.yzq.kotlincommon.ui.hybrid

import android.webkit.JavascriptInterface
import com.yzq.logger.Logger

/**
 * @description: 提供给webview调用的方法
 * @author : XeonYu
 * @date : 2020/8/6
 * @time : 11:10
 */

object JsBridge {

    @JavascriptInterface
    fun nativeFun() {
        Logger.i("webView调本地方法")
    }
}
