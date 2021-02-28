package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.viewModelScope
import com.yzq.common.net.constants.ApiConstants
import com.yzq.common.net.view_model.ApiServiceViewModel
import kotlinx.coroutines.launch


class DownloadViewModel : ApiServiceViewModel() {


    fun downloadApk() {

//
//        launchProgressDialog(ApiConstants.apk, "下载") {
//
//            apiServiceModel.downloadApk()
//
//        }

        viewModelScope.launch {
            apiServiceModel.downloadApk()
        }


    }


}