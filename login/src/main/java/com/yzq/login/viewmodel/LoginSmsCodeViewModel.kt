package com.yzq.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yzq.base.view_model.BaseViewModel
import com.yzq.logger.Logger

/**
 * @author : yuzhiqiang
 * @description: 验证码登录
 */
class LoginSmsCodeViewModel : BaseViewModel() {

    //按钮是否可用
    private val _btnEnable = MutableLiveData<Boolean>()
    val btnEnable: LiveData<Boolean> = _btnEnable


    //toast提示
    var toast: MutableLiveData<String> = MutableLiveData("")

    //是否同意协议
    private var _isAgreementChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAgreementChecked: LiveData<Boolean> = _isAgreementChecked

    //获取验证码按钮是否可用
    private val _smsCodeBtnEnable = MutableLiveData<Boolean>()
    val smsCodeBtnEnable: LiveData<Boolean> = _smsCodeBtnEnable

    //开始倒计时
    private val _startCountDown = MutableLiveData<Boolean>()
    val startCountDown: LiveData<Boolean> = _startCountDown


    //协议弹窗
    var showAgreementDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    private var phone = ""
    private var smsCode = ""


    fun changePhone(it: String) {
        this.phone = it
        checkBtnEnable()
    }


    /**
     * 短信验证码
     * @param content String
     */
    fun changeSmsCode(content: String) {
        this.smsCode = content
        checkBtnEnable()
    }

    private fun checkBtnEnable() {
        _smsCodeBtnEnable.value = phone.isNotEmpty() && phone.length == 11

        //下一步按钮
        _btnEnable.value = phone.isNotEmpty() && smsCode.isNotEmpty()
    }

    /**
     * 发送短信验证码
     */
    fun sendSmsCode() {

        Logger.it(TAG, "发送短信验证码")

        _startCountDown.value = true
    }

    fun changeAgreementChecked(isChecked: Boolean) {
        this._isAgreementChecked.value = isChecked
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
