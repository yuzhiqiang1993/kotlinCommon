package com.yzq.kotlincommon.ui.activity

import android.view.View
import androidx.activity.OnBackPressedCallback
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.common.constants.RoutePath
import com.yzq.cordova_webcontainer.CordovaWebContainer
import com.yzq.cordova_webcontainer.CordovaWebContainerActivity
import com.yzq.cordova_webcontainer.observer.PageObserver
import com.yzq.kotlincommon.databinding.ActivityWebViewBinding

@Route(path = RoutePath.Main.WEB_VIEW)
class WebViewActivity : CordovaWebContainerActivity() {

    private lateinit var binding: ActivityWebViewBinding

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.webContainer.canGoBack()) {
                binding.webContainer.goBack()
            } else {
                finishAfterTransition()
            }

        }

    }

    override fun initContentView() {
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun initWebContainer(): CordovaWebContainer {


        binding.webContainer.apply {
            init(this@WebViewActivity, this@WebViewActivity)
//            webviewClient.interceptRequest { view, request, response ->
//
//            }
//            webviewClient.overrideUrlLoading { view, request ->
//                return@overrideUrlLoading false
//            }

            addPagePbserver(object : PageObserver {
                override fun onReceivedTitle(title: String) {
                    binding.includedToolbar.toolbar.title = title
                }

                override fun onProgressChanged(newProgress: Int) {
                    binding.progressBar.progress = newProgress
                    if (newProgress >= 100) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            })
        }
        return binding.webContainer
    }

    override fun initWidget() {
        super.initWidget()

        initToolbar(binding.includedToolbar.toolbar, "WebView")

        onBackPressedDispatcher.addCallback(this, backPressedCallback)

//        val url = "http://ng.mobile.ant.design/#/kitchen-sink?lang=zh-CN"
        binding.webContainer.loadUrl()

    }


}
