package com.yzq.common.net.api

import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.yzq.common.data.movie.MovieBean
import com.yzq.common.net.FileRetrofitFactory
import com.yzq.common.net.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ApiServiceModel {
    suspend fun downloadApk() {

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

    suspend fun getData(start: Int, count: Int): MovieBean = withContext(Dispatchers.IO) {
        RetrofitFactory.instance.getService(ApiService::class.java)
            .getMovies("0b2bdeda43b5688921839c8ecb20399b", start, count)
    }

}