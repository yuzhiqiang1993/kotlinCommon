package com.yzq.login.ui.complete

import android.view.View
import androidx.activity.viewModels
import com.therouter.TheRouter
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.AgreementViewModel
import com.yzq.binding.viewBinding
import com.yzq.logger.Logger.it
import com.yzq.login.R
import com.yzq.login.databinding.ActivityLoginBySmsCodeBinding
import com.yzq.login.ui.BaseLoginActivity
import com.yzq.login.ui.dialog.AgreementDialog
import com.yzq.login.view_model.LoginSmsCodeViewModel
import com.yzq.router.RoutePath


/**
 * @description: 通过短信验证码登录页面
 * @author : yuzhiqiang
 */
@Route(path = RoutePath.Login.LOGIN_BY_SMS)
class LoginBySmsCodeActivity : BaseLoginActivity() {
    private val binding by viewBinding(ActivityLoginBySmsCodeBinding::inflate)

    private val veiwModel: LoginSmsCodeViewModel by viewModels()

    private val agreementViewModel: AgreementViewModel by viewModels()

    private var agreementDialog: AgreementDialog? = null

    override fun initVariable() {
        agreementDialog = AgreementDialog(this, R.string.login_agreement_dialog)
    }


    override fun initListener() {

        agreementDialog?.onBtnClick {
            veiwModel.changeAgreementChecked(it)
            if (it) {
                veiwModel.login()
            }
        }

        binding.run {
            ivClose.setOnClickListener { view: View? ->
                finish()
            }

            tvLoginByPwd.setOnClickListener { v: View? ->
                //密码登录
                it(TAG, "密码登录")
                TheRouter.build(RoutePath.Login.LOGIN_BY_PWD).navigation()
                finish()
            }

            inputPhone.onContentChange { phone ->
                veiwModel.changePhone(phone)
            }

            inputSmsCode.run {
                onContentChange { smsCode ->
                    veiwModel.changeSmsCode(smsCode)
                }
                onSmsBtnClick {
                    //获取验证码
                    it(TAG, "获取验证码")
                    veiwModel.sendSmsCode()
                }
            }


            agreementCheckbox.onAgreementChecked { isChecked ->
                it(TAG, "协议选中:$isChecked")
                veiwModel.changeAgreementChecked(isChecked)
            }
            agreementCheckbox.onAgreementClick { content ->
                agreementViewModel.agreementClick(content)
            }

            btnLogin.setOnClickListener { view: View? ->
                veiwModel.login()
            }

        }
    }

    override fun observeViewModel() {
        veiwModel.run {

            btnEnable.observe(this@LoginBySmsCodeActivity) { enable: Boolean ->
                binding.btnLogin.isEnabled = enable
            }

            isAgreementChecked.observe(this@LoginBySmsCodeActivity) { isChecked: Boolean ->
                binding.agreementCheckbox.changeCheckState(isChecked)
            }

            smsCodeBtnEnable.observe(this@LoginBySmsCodeActivity) {
                binding.inputSmsCode.changeSmsBtnEnable(it)
            }

            startCountDown.observe(this@LoginBySmsCodeActivity) {
                binding.inputSmsCode.startCountdown()
            }

            showAgreementDialog.observe(
                this@LoginBySmsCodeActivity
            ) { show: Boolean ->
                if (show) {
                    //显示协议弹窗
                    it(TAG, "显示协议弹窗")
                    agreementDialog!!.safeShow()
                }
            }
        }
    }


}