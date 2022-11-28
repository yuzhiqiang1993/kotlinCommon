package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.view.KeyEvent
import android.webkit.*
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.LogUtils
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityWebViewBinding
import com.yzq.kotlincommon.ui.hybrid.JsBridge

@Route(path = RoutePath.Main.WEB_VIEW)
class WebViewActivity : BaseActivity() {

    private val binding by viewbind(ActivityWebViewBinding::inflate)

    @SuppressLint("SetJavaScriptEnabled")
    override fun initWidget() {
        super.initWidget()

        initToolbar(binding.includedToolbar.toolbar, "WebView")

        binding.webview.webViewClient = WebViewClient()
        val settings = binding.webview.settings
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.domStorageEnabled = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        binding.webview.webViewClient = webViewClient
        binding.webview.addJavascriptInterface(JsBridge, "Android")

//        val url = "http://192.168.8.121:4200"

        val url = "http://ng.mobile.ant.design/#/kitchen-sink?lang=zh-CN"
        binding.webview.loadUrl(url)
    }

//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        LogUtils.i("====onWindowFocusChanged====")
//
//        val height = resources.displayMetrics.heightPixels // 屏幕高度
//
//        /*解决H5 页面输入框弹出异常的问题*/
//        AndroidBug5497Workaround.assistActivity(this, height)
//    }

    private val webViewClient = object : WebViewClient() {

        /*加载页面资源时调用  每个资源被加载时都会调用*/

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
            binding.webview.loadUrl("javascript:window.test()")
            binding.webview.loadUrl("javascript:test()")
            binding.webview.loadUrl("javascript:window.webFun()")
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?,
        ): Boolean {
            LogUtils.i("shouldOverrideUrlLoading request:${request!!.url}")
            return super.shouldOverrideUrlLoading(view, request)
        }

        /*加载错误时调用*/

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?,
        ) {
            LogUtils.e("onReceivedError:${request!!.url}")
            LogUtils.e("onReceivedError:${error!!.errorCode}")
            super.onReceivedError(view, request, error)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (binding.webview.canGoBack()) {
                binding.webview.goBack()
                return true
            }
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        binding.webview.clearHistory()
        binding.webview.clearCache(true)
        binding.webview.destroy()
        super.onDestroy()
    }
}
