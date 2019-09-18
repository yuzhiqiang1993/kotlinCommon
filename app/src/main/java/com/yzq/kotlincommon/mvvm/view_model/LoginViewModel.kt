package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.yzq.kotlincommon.mvvm.model.MoviesModel
import com.yzq.lib_base.view_model.BaseViewModel
import com.yzq.lib_net.net.GsonConvert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel : BaseViewModel() {


    private var model = MoviesModel()
    var loginData = MutableLiveData<Boolean>()



    fun loginWithCoroutine() {
        launchLoadingDialog {

            val movieBean = withContext(Dispatchers.IO) {
                model.getData(0, 1)
            }
            LogUtils.i("请求完成：" + GsonConvert.toJson(movieBean))

            loginData.value = true
        }
    }
}