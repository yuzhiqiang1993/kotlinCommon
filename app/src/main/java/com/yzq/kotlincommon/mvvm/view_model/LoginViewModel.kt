package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.yzq.kotlincommon.mvvm.model.MoviesModel
import com.yzq.lib_base.view_model.BaseViewModel
import com.yzq.common.net.net.GsonConvert

class LoginViewModel : BaseViewModel() {


    private val model by lazy { MoviesModel() }
    val loginData by lazy { MutableLiveData<Boolean>() }


    fun login() {
        launchLoadingDialog {

            val movieBean = model.getData(0, 1)
            LogUtils.i("请求完成：" + GsonConvert.toJson(movieBean))

            loginData.value = true
        }
    }
}