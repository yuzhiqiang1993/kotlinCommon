package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.net.GsonConvert
import com.yzq.common.net.view_model.ApiServiceViewModel

class LoginViewModel : ApiServiceViewModel() {


    val loginData by lazy { MutableLiveData<Boolean>() }


    fun login() {
        launchLoadingDialog {

            val movieBean = apiServiceModel.getData(0, 1)
            LogUtils.i("请求完成：" + GsonConvert.toJson(movieBean))

            loginData.value = true
        }
    }
}