package com.yumc.android.userauth.login.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yzq.base.view_model.BaseViewModel
import com.yzq.logger.Logger


/**
 *
 * @description: 忘记密码页面ViewModel
 * @author : yuzhiqiang
 *
 */

class RetrievePwdViewModel : BaseViewModel() {

    //按钮是否可用
    private val _btnEnable = MutableLiveData<Boolean>()
    val btnEnable: LiveData<Boolean> = _btnEnable

    //获取验证码按钮是否可用
    private val _smsCodeBtnEnable = MutableLiveData<Boolean>()
    val smsCodeBtnEnable: LiveData<Boolean> = _smsCodeBtnEnable

    //开始倒计时
    private val _startCountDown = MutableLiveData<Boolean>()
    val startCountDown: LiveData<Boolean> = _startCountDown

    //手机号
    private var phone: String = ""

    //验证码
    private var smsCode: String = ""

    //密码
    private var pwd: String = ""


    fun changePwd(it: String) {
        pwd = it


    }

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


}