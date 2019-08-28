package com.yzq.kotlincommon.mvvm.view_model

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.extend.transform
import com.yzq.common.mvvm.view_model.BaseViewModel
import com.yzq.common.rx.BaseDialogObserver
import com.yzq.data_constants.data.movie.MovieBean
import com.yzq.kotlincommon.mvvm.model.MoviesModel

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

}