package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.KeyEvent
import android.webkit.*
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.JsBridge
import com.yzq.kotlincommon.R
import com.yzq.lib_base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_web_view.*


@Route(path = RoutePath.Main.WEB_VIEW)
class WebViewActivity : BaseActivity() {

    override fun getContentLayoutId() = R.layout.activity_web_view

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webview.webViewClient = WebViewClient()
        val settings = webview.settings
        settings.javaScriptEnabled = true
        settings.setJavaScriptCanOpenWindowsAutomatically(true)
        settings.setDomStorageEnabled(true)
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webview.webViewClient = webViewClient
        webview.addJavascriptInterface(JsBridge, "Android")
        WebView.setWebContentsDebuggingEnabled(true)
    }

    override fun initWidget() {
        super.initWidget()

        val url = "http://192.168.8.121:4200"
        webview.loadUrl(url)


    }


    val webViewClient = object : WebViewClient() {
        /*历史记录被更新时调用*/
        override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
            super.doUpdateVisitedHistory(view, url, isReload)
        }


        /*应用程序重新请求网页数据时调用*/
        override fun onFormResubmission(view: WebView?, dontResend: Message?, resend: Message?) {
            super.onFormResubmission(view, dontResend, resend)
        }


        /*加载页面资源时调用  每个资源被加载时都会调用*/

        override fun onLoadResource(view: WebView?, url: String?) {
            super.onLoadResource(view, url)
        }

        /*页面开始加载时调用 */

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }


        /*页面加载完毕时调用 */

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            /*调web端方法*/
            LogUtils.i("页面加载完毕  调js方法")

            /*
            *angular端需要将方法挂载到window对象中  否则会报方法未定义的错误
            *例如: window['test'] = function() {
            *        console.log('===============angularFun======================>');
            *       };
            * */
            webview.loadUrl("javascript:window.test()")
            webview.loadUrl("javascript:test()")
            webview.loadUrl("javascript:window.webFun()")

        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            LogUtils.i("shouldOverrideUrlLoading request:${request!!.url}")
            return super.shouldOverrideUrlLoading(view, request)
        }

        /*加载错误时调用*/

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            LogUtils.e("onReceivedError:${request!!.url}")
            LogUtils.e("onReceivedError:${error!!.errorCode}")
            super.onReceivedError(view, request, error)
        }

        /*重写此方法可以处理网页中的事件*/
        override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
            return super.shouldOverrideKeyEvent(view, event)
        }


        /*拦截请求*/
        override fun shouldInterceptRequest(
            view: WebView?,
            request: WebResourceRequest?
        ): WebResourceResponse? {
            return super.shouldInterceptRequest(view, request)
        }

    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview.canGoBack()) {
                webview.goBack()
                return true
            }
        }

        return super.onKeyDown(keyCode, event)
    }


    override fun onDestroy() {
        webview.clearHistory()
        webview.clearCache(true)
        webview.destroy()
        super.onDestroy()
    }


}