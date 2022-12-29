package com.yzq.kotlincommon.ui.activity

import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.common.net.FileRetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.common.net.constants.ApiConstants
import com.yzq.coroutine.safety_coroutine.launchSafety
import com.yzq.coroutine.safety_coroutine.withIO
import com.yzq.kotlincommon.databinding.ActivityDownloadBinding
import com.yzq.materialdialog.changeProgress
import com.yzq.materialdialog.changeTitle
import com.yzq.materialdialog.newProgressDialog
import com.yzq.permission.getPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo

@Route(path = RoutePath.Main.DOWNLOAD)
class DownloadActivity : BaseActivity() {

    private val binding by viewbind(ActivityDownloadBinding::inflate)

    private val progressDialog by lazy { newProgressDialog() }

    override fun initWidget() {

        initToolbar(binding.includedToolbar.toolbar, "网络进度")

        binding.btnDownload
            .setOnClickListener {
                getPermissions(PermissionConstants.STORAGE) {

                    LogUtils.i("有以下权限:$it")
                    downloadApk()
                }
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
                        progressDialog.dismiss()
                    }
                }
            )
    }

    private fun downloadApk() {

        progressDialog.changeTitle("下载中...").show()
        lifecycleScope.launchSafety {
            val savePath = withIO {
                val download = FileRetrofitFactory.instance.getService(ApiService::class.java)
                    .downloadApk()

                LogUtils.i("""总长度：${download.contentLength()}""")

                val path =
                    "${PathUtils.getExternalAppFilesPath()}/yzq.apk"

                val su = withContext(Dispatchers.IO) {
                    FileIOUtils.writeFileFromIS(path, download.byteStream())
                }

                LogUtils.i("存储路径：$path")
                LogUtils.i("文件写入完成:$su")

                path
            }

            AppUtils.installApp(savePath)
        }
    }
}
