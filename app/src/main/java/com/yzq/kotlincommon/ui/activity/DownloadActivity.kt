package com.yzq.kotlincommon.ui.activity

import androidx.lifecycle.lifecycleScope
import com.therouter.router.Route
import com.yzq.application.AppManager
import com.yzq.application.AppStorage
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.writeFileFromIS
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.common.constants.RoutePath
import com.yzq.common.net.FileRetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.common.net.constants.ApiConstants
import com.yzq.coroutine.ext.launchSafety
import com.yzq.coroutine.ext.withIO
import com.yzq.dialog.ProgressDialog
import com.yzq.kotlincommon.databinding.ActivityDownloadBinding
import com.yzq.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo

@Route(path = RoutePath.Main.DOWNLOAD)
class DownloadActivity : BaseActivity() {

    private val binding by viewBinding(ActivityDownloadBinding::inflate)

    private val progressDialog by lazy { ProgressDialog(this) }

    override fun initWidget() {

        initToolbar(binding.includedToolbar.toolbar, "网络进度")

        binding.btnDownload
            .setOnClickListener {
                /**
                 * 要想在外部存储的公共目录中写文件，要申请文件管理权限，适配Android11
                 */
//                getPermissions(Permission.MANAGE_EXTERNAL_STORAGE) {
//                    Logger.i("有以下权限:$it")
                downloadApk()
//                }
            }
    }

    override fun initListener() {

        ProgressManager.getInstance()
            .addResponseListener(
                ApiConstants.apk,
                object : ProgressListener {
                    override fun onProgress(progressInfo: ProgressInfo) {
                        progressDialog.changeProgress(progressInfo.percent)
                    }

                    override fun onError(id: Long, e: Exception?) {
                        progressDialog.safeDismiss()
                    }
                }
            )
    }

    private fun downloadApk() {
        progressDialog.changeTitle("下载中...").safeShow()
        lifecycleScope.launchSafety {
            val savePath = withIO {
                val download = FileRetrofitFactory.instance.getService(ApiService::class.java)
                    .downloadApk()
                Logger.i("""总长度：${download.contentLength()}""")

                val path =
                    "${AppStorage.External.Private.downloadPath}yzq.apk"

                val su = withContext(Dispatchers.IO) {
                    writeFileFromIS(path, download.byteStream())
                }

                Logger.i("存储路径：$path")
                Logger.i("文件写入完成:$su")

                path
            }
            progressDialog.safeDismiss()

            AppManager.installApk(savePath, "${AppManager.application.packageName}.provider")
        }
    }
}
