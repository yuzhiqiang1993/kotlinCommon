package com.yzq.login.ui.popup

import android.view.View
import androidx.activity.viewModels
import com.therouter.TheRouter
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.AgreementViewModel
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.logger.Logger.it
import com.yzq.login.R
import com.yzq.login.databinding.ActivityLoginBySmsCodePopupBinding
import com.yzq.login.ui.BasePopupActivity
import com.yzq.login.ui.dialog.AgreementDialog
import com.yzq.login.view_model.LoginSmsCodeViewModel


/**
 * @description: 短信验证码登录页面(popup)
 * @author : yuzhiqiang
 */

@Route(path = RoutePath.Login.LOGIN_BY_SMS_POPUP)
class LoginBySmsCodePopupActivity : BasePopupActivity() {

    private val binding: ActivityLoginBySmsCodePopupBinding by viewbind(
        ActivityLoginBySmsCodePopupBinding::inflate
    )
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
            popupHeader.run {
                onIvBackClick {
                    finish()
                    TheRouter.build(RoutePath.Login.ONE_KEY_LOGIN_POPUP).navigation()
                }
                onIvCloseClick {
                    finish()
                }
            }

            popupTitle.titleEndOnClick {
                //密码登录
                finish()
                TheRouter.build(RoutePath.Login.LOGIN_BY_PWD_POPUP).navigation()

            }

            inputSmsCode.setOnClickListener { v: View? ->
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

            btnEnable.observe(this@LoginBySmsCodePopupActivity) { enable: Boolean ->
                binding.btnLogin.isEnabled = enable
            }

            isAgreementChecked.observe(this@LoginBySmsCodePopupActivity) { isChecked: Boolean ->
                binding.agreementCheckbox.changeCheckState(isChecked)
            }

            smsCodeBtnEnable.observe(this@LoginBySmsCodePopupActivity) {
                binding.inputSmsCode.changeSmsBtnEnable(it)
            }

            startCountDown.observe(this@LoginBySmsCodePopupActivity) {
                binding.inputSmsCode.startCountdown()
            }

            showAgreementDialog.observe(
                this@LoginBySmsCodePopupActivity
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