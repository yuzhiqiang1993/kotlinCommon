package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.view.KeyEvent
import android.webkit.*
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.JsBridge
import com.yzq.kotlincommon.databinding.ActivityWebViewBinding
import com.yzq.lib_base.ui.activity.BaseViewBindingActivity


@Route(path = RoutePath.Main.WEB_VIEW)
class WebViewActivity : BaseViewBindingActivity<ActivityWebViewBinding>() {


    override fun getViewBinding() = ActivityWebViewBinding.inflate(layoutInflater)


    @SuppressLint("SetJavaScriptEnabled")
    override fun initWidget() {
        super.initWidget()


        initToolbar(binding.layoutToolbar.toolbar, "WebView")


        binding.webview.webViewClient = WebViewClient()
        val settings = binding.webview.settings
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.domStorageEnabled = true
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        binding.webview.webViewClient = webViewClient
        binding.webview.addJavascriptInterface(JsBridge, "Android")

        val url = "http://192.168.8.121:4200"
        binding.webview.loadUrl(url)


    }


    val webViewClient = object : WebViewClient() {


        /*加载页面资源时调用  每个资源被加载时都会调用*/

        /*页面开始加载时调用 */


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
            binding.webview.loadUrl("javascript:window.test()")
            binding.webview.loadUrl("javascript:test()")
            binding.webview.loadUrl("javascript:window.webFun()")

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