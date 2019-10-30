package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.yzq.kotlincommon.net.ApiConstants
import com.yzq.kotlincommon.net.ApiService
import com.yzq.lib_base.view_model.BaseViewModel
import com.yzq.lib_net.net.FileRetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo
import java.io.File


class DownloadViewModel : BaseViewModel() {


    val percent by lazy { MutableLiveData<Int>() }

    fun downloadApk() {

        launchScope {


            ProgressManager.getInstance()
                .addResponseListener(ApiConstants.apk, object : ProgressListener {
                    override fun onProgress(progressInfo: ProgressInfo?) {

                        LogUtils.i("下载进度:${progressInfo?.percent}")

                        percent.value = progressInfo?.percent

                    }

                    override fun onError(id: Long, e: Exception?) {

                        LogUtils.i("下载出错：${e?.printStackTrace()}")
                    }

                })



            withContext(Dispatchers.IO) {

                val download = FileRetrofitFactory.instance.getService(ApiService::class.java)
                    .downloadApk()

                LogUtils.i("""总长度：${download.byteStream().available()}""")


                val path = PathUtils.getExternalStoragePath() + File.separator + "kotlinCommon/yzq.apk"

                LogUtils.i("存储路径：${path}")
                val su =
                    FileIOUtils.writeFileFromIS(path, download.byteStream())

                LogUtils.i("文件写入完成:${su}")
            }


        }
    }


}