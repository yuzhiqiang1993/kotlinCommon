package com.yumc.android.userauth.login.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yzq.baseui.BaseViewModel
import com.yzq.logger.Logger


/**
 *
 * @description: 完善注册信息页面的 ViewModel
 * @author : yuzhiqiang
 *
 */

class CompleteRegisterInfoViewModel : BaseViewModel() {

    //按钮是否可用
    private val _btnEnable = MutableLiveData<Boolean>()
    val btnEnable: LiveData<Boolean> = _btnEnable

    //密码
    private var pwd: String = ""


    fun changePwd(it: String) {
        pwd = it
        _btnEnable.value = pwd.isNotEmpty()

    }


    /**
     * 完成注册
     */
    fun completeRegister() {
        Logger.it(TAG, "完成注册")


    }
}