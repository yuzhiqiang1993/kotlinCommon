package com.yumc.android.userauth.login.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yzq.baseui.BaseViewModel


/**
 *
 * @description:  免登录页面ViewModel
 * @author : yuzhiqiang
 *
 */

class ExemptLoginViewModel : BaseViewModel() {


    //是否同意协议
    private var _isAgreementChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAgreementChecked: LiveData<Boolean> = _isAgreementChecked


    fun agreementChecked(checked: Boolean) {
        this._isAgreementChecked.value = checked
    }

    fun oneKeyLogin() {


    }

    fun exemptLogin() {

    }

}