package com.yzq.kotlincommon.mvvm.view_model

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.yzq.kotlincommon.data.movie.MovieBean
import com.yzq.kotlincommon.mvvm.model.MoviesModel
import com.yzq.lib_base.rx.BaseDialogObserver
import com.yzq.lib_base.view_model.BaseViewModel
import com.yzq.lib_net.net.GsonConvert
import com.yzq.lib_rx.transform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel : BaseViewModel() {


    private var model = MoviesModel()
    var loginData = MutableLiveData<Boolean>()


    @SuppressLint("CheckResult")
    fun login() {

        LogUtils.i("Login")


        model.getData(0, 10)
            .transform(lifecycleOwner)
            .subscribe(object : BaseDialogObserver<MovieBean>(this) {
                override fun onSuccess(t: MovieBean) {
                    loginData.value = true
                }

            })

    }


    fun loginWithCoroutine() {
        launchLoadingDialog {

            val movieBean = withContext(Dispatchers.IO) {
                model.getData1(0, 1)
            }
            LogUtils.i("请求完成：" + GsonConvert.toJson(movieBean))

            loginData.value = true
        }
    }
}