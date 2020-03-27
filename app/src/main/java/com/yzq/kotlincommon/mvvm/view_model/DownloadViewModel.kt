package com.yzq.kotlincommon.mvvm.view_model

import com.yzq.common.net.constants.ApiConstants
import com.yzq.common.net.view_model.ApiServiceViewModel


class DownloadViewModel : ApiServiceViewModel() {


    fun downloadApk() {


        launchProgressDialog(ApiConstants.apk, "下载") {

            apiServiceModel.downloadApk()

        }


    }


}