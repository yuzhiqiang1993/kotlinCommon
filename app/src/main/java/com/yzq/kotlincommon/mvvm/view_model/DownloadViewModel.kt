package com.yzq.kotlincommon.mvvm.view_model

import com.yzq.kotlincommon.mvvm.model.DownLoadModel
import com.yzq.kotlincommon.net.ApiConstants
import com.yzq.lib_base.view_model.BaseViewModel


class DownloadViewModel : BaseViewModel() {


    private val downLoadModel by lazy { DownLoadModel() }

    fun downloadApk() {


        launchProgressDialog(ApiConstants.apk, "下载") {

            downLoadModel.downloadApk()

        }


    }


}