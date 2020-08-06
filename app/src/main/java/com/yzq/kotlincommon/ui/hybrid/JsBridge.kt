package com.yzq.kotlincommon

import android.webkit.JavascriptInterface
import com.blankj.utilcode.util.LogUtils


/**
 * @description: 提供给webview调用的方法
 * @author : XeonYu
 * @date   : 2020/8/6
 * @time   : 11:10
 */

object JsBridge {


    @JavascriptInterface
    fun nativeFun() {
        LogUtils.i("webView调本地方法")
    }


}