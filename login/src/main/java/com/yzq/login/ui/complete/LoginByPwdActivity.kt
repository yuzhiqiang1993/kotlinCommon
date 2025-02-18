package com.yzq.login.ui.complete

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.therouter.TheRouter
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.AgreementViewModel
import com.yzq.binding.viewBinding
import com.yzq.logger.Logger
import com.yzq.login.R
import com.yzq.login.databinding.ActivityLoginByPwdBinding
import com.yzq.login.ui.BaseLoginActivity
import com.yzq.login.ui.dialog.AgreementDialog
import com.yzq.login.view_model.LoginPwdViewModel
import com.yzq.router.RoutePath

/**
 * @author : yuzhiqiang
 * @description: 密码登录页
 */

@Route(path = RoutePath.Login.LOGIN_BY_PWD)
class LoginByPwdActivity : BaseLoginActivity() {

    private val binding by viewBinding(ActivityLoginByPwdBinding::inflate)

    private val loginByPwdViewModel: LoginPwdViewModel by viewModels()

    private val agreementViewModel: AgreementViewModel by viewModels()

    private var agreementDialog: AgreementDialog? = null

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginByPwdActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initVariable() {
        agreementDialog = AgreementDialog(this, R.string.login_agreement_dialog)
    }


    override fun initListener() {

        agreementDialog?.onBtnClick {
            loginByPwdViewModel.changeAgreementChecked(it)
            if (it) {
                loginByPwdViewModel.login()
            }
        }

        binding.run {
            ivClose.setOnClickListener { view: View? ->
                finish()
            }

            tvLoginBySms.setOnClickListener { v: View? ->
                //验证码登录
                Logger.it(TAG, "验证码登录")
                TheRouter.build(RoutePath.Login.LOGIN_BY_SMS).navigation()
                finish()
            }

            inputPhone.onContentChange { phone ->
                loginByPwdViewModel.changePhone(phone)
            }
            inputPwd.onContentChange { pwd ->
                loginByPwdViewModel.changePwd(pwd)
            }

            agreementCheckbox.onAgreementChecked { isChecked ->
                Logger.it(TAG, "协议选中:$isChecked")
                loginByPwdViewModel.changeAgreementChecked(isChecked)
            }
            agreementCheckbox.onAgreementClick { content ->
                agreementViewModel.agreementClick(content)
            }


            btnLogin.setOnClickListener { view: View? ->
                loginByPwdViewModel.login()
            }

            tvForgetPwd.setOnClickListener { view: View? ->
                //忘记密码
                Logger.it(TAG, "忘记密码")
                TheRouter.build(RoutePath.Login.RETIREVE_PWD).navigation()
            }
        }
    }

    override fun observeViewModel() {
        loginByPwdViewModel.run {

            btnEnable.observe(this@LoginByPwdActivity) { enable: Boolean ->
                binding.btnLogin.isEnabled = enable
            }

            isAgreementChecked.observe(this@LoginByPwdActivity) { isChecked: Boolean ->
                binding.agreementCheckbox.changeCheckState(isChecked)
            }

            loginByPwdViewModel.showAgreementDialog.observe(
                this@LoginByPwdActivity
            ) { show: Boolean ->
                if (show) {
                    //显示协议弹窗
                    Logger.it(TAG, "显示协议弹窗")
                    agreementDialog!!.safeShow()
                }
            }
        }
    }

}