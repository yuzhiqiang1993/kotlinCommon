package com.yzq.kotlincommon.view_model

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.data.LoginBean
import com.yzq.common.net.view_model.ApiServiceViewModel
import com.yzq.common.utils.MMKVUtil

class LoginViewModel : ApiServiceViewModel() {

    val loginLiveData by lazy { MutableLiveData<LoginBean>() }

    fun login(account: String, pwd: String) {
        launchLoadingDialog {

            MMKVUtil.account = account
            MMKVUtil.pwd = pwd

            LogUtils.i("MMKVUtil.account = ${MMKVUtil.account}")
            LogUtils.i("MMKVUtil.account = ${MMKVUtil.pwd}")


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