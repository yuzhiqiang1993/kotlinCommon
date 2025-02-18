package com.yumc.android.userauth.login.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yzq.baseui.BaseViewModel


/**
 *
 * @description: 忘记密码页面ViewModel
 * @author : yuzhiqiang
 *
 */

class SetNewPwdViewModel : BaseViewModel() {

    //按钮是否可用
    private val _btnEnable = MutableLiveData<Boolean>()
    val btnEnable: LiveData<Boolean> = _btnEnable

    //密码
    private var pwd: String = ""


    fun changePwd(it: String) {
        pwd = it
        _btnEnable.value = it.isNotEmpty()

    }

    fun setNewPwd() {


    }
}