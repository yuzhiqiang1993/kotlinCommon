package com.yzq.kotlincommon.ui.activity

import android.net.Uri
import android.os.Build
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.hjq.permissions.Permission
import com.therouter.router.Route
import com.yzq.application.AppStorage
import com.yzq.cordova_webcontainer.CordovaWebContainer
import com.yzq.cordova_webcontainer.CordovaWebContainerActivity
import com.yzq.cordova_webcontainer.core.CordovaWebviewChormeClient
import com.yzq.cordova_webcontainer.observer.PageObserver
import com.yzq.kotlincommon.databinding.ActivityWebViewBinding
import com.yzq.logger.Logger
import com.yzq.permission.getPermissions
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar
import org.apache.cordova.LOG
import java.io.File

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
            init(this@WebViewActivity, LOG.VERBOSE)
//            webviewClient.interceptRequest { view, request, response ->
//
//            }
//            webviewClient.overrideUrlLoading { view, request ->
//                return@overrideUrlLoading false
//            }
            setWebviewChormeClient(object :
                CordovaWebviewChormeClient(webViewEngine) {

                override fun onShowFileChooser(
                    webView: WebView,
                    filePathsCallback: ValueCallback<Array<Uri>>,
                    fileChooserParams: FileChooserParams
                ): Boolean {
                    /**
                     * 拦截前端 input标签  capture="camera" 表示调用相机
                     */
                    if (fileChooserParams.isCaptureEnabled) {
                        kotlin.runCatching {
                            takePhoto(filePathsCallback)
                        }.onFailure {
                            it.printStackTrace()
                        }
                        return true
                    }
                    return super.onShowFileChooser(webView, filePathsCallback, fileChooserParams)
                }

            })



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


    /*回调给前端*/
    private var valueCallback: ValueCallback<Array<Uri>>? = null

    /*暂存拍照图片的photoUri*/
    private var photoUri: Uri? = null

    private val takePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                // 处理拍照成功的情况
                Logger.i("拍照成功：${photoUri}")
                photoUri?.let {
                    valueCallback?.onReceiveValue(arrayOf(it))
                }

            } else {
                // 处理拍照失败或取消的情况
                Logger.i("拍照失败或取消")
                valueCallback?.onReceiveValue(null)
            }
        }

    private fun takePhoto(filePathsCallback: ValueCallback<Array<Uri>>) {

        getPermissions(Permission.CAMERA) {

            this.valueCallback = filePathsCallback

            val cordovaImgDir = File(AppStorage.External.Private.cachePath, "cordova_img")
            Logger.i("cordovaImgDir: ${cordovaImgDir.path}")
            /*尝试删除以前的缓存图片*/
            if (cordovaImgDir.exists()) {
                cordovaImgDir.deleteRecursively()
            }
            if (!cordovaImgDir.exists()) {
                cordovaImgDir.mkdirs()
            }

            val photoFile = File(cordovaImgDir, "${System.currentTimeMillis()}.png")
            Logger.i("photoFile: ${photoFile.path}")

            photoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                /*适配Android 7.0以上版本 */
                FileProvider.getUriForFile(this, "${packageName}.provider", photoFile)
            } else {
                Uri.fromFile(photoFile)
            }

            photoUri?.also {
                takePhotoLauncher.launch(it)
            }


        }


    }

}
