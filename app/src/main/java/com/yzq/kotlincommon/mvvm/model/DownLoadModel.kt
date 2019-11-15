package com.yzq.kotlincommon.mvvm.model

import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.yzq.kotlincommon.net.ApiService
import com.yzq.common.net.net.FileRetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class DownLoadModel {


    suspend fun downloadApk(){

        withContext(Dispatchers.IO) {

            val download = FileRetrofitFactory.instance.getService(ApiService::class.java)
                .downloadApk()

            LogUtils.i("""总长度：${download.byteStream().available()}""")


            val path =
                PathUtils.getExternalStoragePath() + File.separator + "kotlinCommon/yzq.apk"

            LogUtils.i("存储路径：${path}")
            val su =
                FileIOUtils.writeFileFromIS(path, download.byteStream())

            LogUtils.i("文件写入完成:${su}")
        }
    }
}