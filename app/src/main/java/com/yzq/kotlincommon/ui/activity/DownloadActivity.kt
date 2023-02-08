package com.yzq.kotlincommon.ui.activity

import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.AppStorage
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
                /**
                 * 要想在外部存储的公共目录中写文件，要申请文件管理权限，适配Android11
                 */
//                getPermissions(Permission.MANAGE_EXTERNAL_STORAGE) {
//                    LogUtils.i("有以下权限:$it")
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


                /**
                 * Path  公共目录需要先申请管理所有文件的权限,一个准则是能在app私有目录中解决就不要向公共目录中写入数据
                 */
//                val path =
//                    "${AppStorage.External.Public.downloadPath}${packageName}${File.separator}yzq.apk"

                val path =
                    "${AppStorage.External.Private.downloadPath}yzq.apk"

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
