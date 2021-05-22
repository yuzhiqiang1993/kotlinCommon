package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.data.LoginBean
import com.yzq.common.net.view_model.ApiServiceViewModel
import com.yzq.common.utils.LocalSpUtils
import kotlinx.coroutines.delay

class LoginViewModel : ApiServiceViewModel() {


    val loginLiveData by lazy { MutableLiveData<LoginBean>() }

    fun login(account: String, pwd: String) {
        launchLoadingDialog {

            LocalSpUtils.account = account
            LocalSpUtils.pwd = pwd

            LogUtils.i("account:${account},pwd:${pwd}")
            delay(1000)

            val loginBean = LoginBean()
            loginBean.account = account
            loginBean.pwd = pwd

//
//            val async = async {
//                withContext(Dispatchers.IO) {
//                    throw Exception("aaaaaa")
//                }
//
//            }
//
//
//            async.await()
//
//            withContext(Dispatchers.IO){
//                throw Exception("bbbbbbbb")
//            }


            loginLiveData.value = loginBean
        }
    }
}