package com.yzq.kotlincommon.view_model

import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.yzq.common.net.FileRetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.common.net.constants.ApiConstants
import com.yzq.common.net.view_model.ApiServiceViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DownloadViewModel : ApiServiceViewModel() {

    fun downloadApk() {

        launchProgressDialog(ApiConstants.apk, "下载中...") {

            val download = FileRetrofitFactory.instance.getService(ApiService::class.java)
                .downloadApk()


            LogUtils.i("""总长度：${download.contentLength()}""")

            val path =
                "${PathUtils.getExternalAppFilesPath()}/yzq.apk"

            val su = withContext(Dispatchers.IO) {
                FileIOUtils.writeFileFromIS(path, download.byteStream())

            }

            LogUtils.i("存储路径：${path}")
            LogUtils.i("文件写入完成:${su}")

            AppUtils.installApp(path)
        }

    }

}