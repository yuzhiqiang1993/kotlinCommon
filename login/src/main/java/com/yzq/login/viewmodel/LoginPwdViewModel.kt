package com.yzq.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yzq.base.view_model.BaseViewModel

/**
 * @author : yuzhiqiang
 * @description: 认证页ViewModel
 */
class LoginPwdViewModel : BaseViewModel() {


    /*登录按钮是否可用*/
    var btnEnable: MutableLiveData<Boolean> = MutableLiveData(false)

    //toast提示
    var toast: MutableLiveData<String> = MutableLiveData("")

    //是否同意协议
    private var _isAgreementChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAgreementChecked: LiveData<Boolean> = _isAgreementChecked


    //协议弹窗
    var showAgreementDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    private var phone = ""
    private var pwd = ""


    fun changePhone(content: String) {
        this.phone = content
        checkBtnEnable()
    }

    fun changePwd(content: String) {
        this.pwd = content
        checkBtnEnable()
    }

    fun changeAgreementChecked(isChecked: Boolean) {
        this._isAgreementChecked.value = isChecked
    }


    /**
     * 检查登录按钮是否可用
     */
    private fun checkBtnEnable() {
        if (phone.length == 11 && !pwd.trim { it <= ' ' }.isEmpty()) {
            btnEnable.setValue(true)
        } else {
            btnEnable.setValue(false)
        }
    }


    fun login() {
        if (!_isAgreementChecked.value!!) {
            //显示协议弹窗
            showAgreementDialog.setValue(true)
        } else {
            showAgreementDialog.setValue(false)
            //doLogin
        }
    }

}
