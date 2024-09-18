package com.yumc.android.userauth.login.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yzq.base.view_model.BaseViewModel


/**
 *
 * @description:  一键登录页面的ViewModel
 * @author : yuzhiqiang
 *
 */

class OneKeyLoginViewModel : BaseViewModel() {


    //是否同意协议
    private var _isAgreementChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAgreementChecked: LiveData<Boolean> = _isAgreementChecked

    //协议弹窗
    val _showAgreementDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    val showAgreementDialog: LiveData<Boolean> = _showAgreementDialog


    fun agreementChecked(checked: Boolean) {
        _isAgreementChecked.value = checked
    }

    fun onekeyLogin() {
        //弹窗
        _showAgreementDialog.value = !_isAgreementChecked.value!!

        if (_isAgreementChecked.value!!) {
            //一键登录
        }
    }
}