package com.yzq.kotlincommon.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yzq.base.view_model.BaseViewModel
import com.yzq.base.view_model.UIState
import com.yzq.common.data.LoginBean
import com.yzq.coroutine.safety_coroutine.launchSafety
import com.yzq.logger.Logger
import com.yzq.storage.mmkv.MMKVUser
import kotlinx.coroutines.delay

class LoginViewModel : BaseViewModel() {

    val loginLiveData by lazy { MutableLiveData<LoginBean>() }

    fun login(account: String, pwd: String) {

        viewModelScope.launchSafety {

            _uiStateFlow.value = UIState.ShowLoadingDialog("登录中...")

            delay(1000)

            MMKVUser.account = account
            MMKVUser.pwd = pwd

            Logger.i("MMKVUtil.account = ${MMKVUser.account}")
            Logger.i("MMKVUtil.account = ${MMKVUser.pwd}")

            val loginBean = LoginBean()
            loginBean.account.set(account)
            loginBean.pwd.set(pwd)

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
            _uiStateFlow.value = UIState.DismissLoadingDialog
//            delay(200)
            loginLiveData.value = loginBean
        }
    }
}
