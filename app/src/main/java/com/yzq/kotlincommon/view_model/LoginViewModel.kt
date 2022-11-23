package com.yzq.kotlincommon.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.yzq.base.view_model.BaseViewModel
import com.yzq.base.view_model.UIState
import com.yzq.common.data.LoginBean
import com.yzq.common.utils.MMKVUtil
import com.yzq.coroutine.scope.launchSafety
import kotlinx.coroutines.delay

class LoginViewModel : BaseViewModel() {

    val loginLiveData by lazy { MutableLiveData<LoginBean>() }

    fun login(account: String, pwd: String) {

        viewModelScope.launchSafety {

            _uiState.value = UIState.ShowLoadingDialog("登录中...")

            delay(1000)

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
            _uiState.value = UIState.DissmissLoadingDialog()
            loginLiveData.value = loginBean
        }
    }
}
