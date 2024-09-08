package com.yzq.login.ui.immediate

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.AgreementViewModel
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.login.R
import com.yzq.login.databinding.ActivityLoginByPwdPopupBinding
import com.yzq.login.ui.BasePopupActivity
import com.yzq.login.ui.dialog.AgreementDialog
import com.yzq.login.viewmodel.LoginPwdViewModel
import floatWithSoftInput


/**
 * @description: 密码登录半屏页面
 * @author : yuzhiqiang
 */
@Route(path = RoutePath.Login.LOGIN_BY_PWD_POPUP)
class LoginByPwdActivityPopupActivity : BasePopupActivity() {

    private val binding: ActivityLoginByPwdPopupBinding by viewbind(ActivityLoginByPwdPopupBinding::inflate)

    private val loginByPwdViewModel: LoginPwdViewModel by viewModels()

    private val agreementViewModel: AgreementViewModel by viewModels()

    private var agreementDialog: AgreementDialog? = null


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginByPwdActivityPopupActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initWidget() {

        binding.run {
            //设置指定View浮动在软键盘上方
            floatWithSoftInput(bottomContent)

            popupHeader.showBack(true)
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
            popupHeader.onIvBackClick {
                //返回到一键登录页面
                finish()
                LoginByOneKeyPopupActivity.start(this@LoginByPwdActivityPopupActivity)
            }

            popupHeader.onIvCloseClick() {
                //返回到菜单
                finish()

            }

            tvLoginBySms.setOnClickListener {
                //验证码登录
            }

            //手机号
            inputPhone.onContentChange {
                loginByPwdViewModel.changePhone(it)
            }

            //密码
            inputPwd.onContentChange {
                loginByPwdViewModel.changePwd(it)
            }

            //协议
            agreementCheckbox.run {
                onAgreementChecked { isChecked ->
                    loginByPwdViewModel.changeAgreementChecked(isChecked)
                }

                onAgreementClick {
                    agreementViewModel.agreementClick(it)
                }

            }

            //登录
            btnLogin.setOnClickListener {
                loginByPwdViewModel.login()
            }

            //忘记密码
            tvForgetPwd.setOnClickListener {
                //跳转到忘记密码页面
                RetrievePwdPopupActivity.start(this@LoginByPwdActivityPopupActivity)
            }
        }

    }


    override fun observeViewModel() {

        loginByPwdViewModel.run {
            btnEnable.observe(this@LoginByPwdActivityPopupActivity) { enable: Boolean? ->
                binding.btnLogin.isEnabled = enable!!
            }

            isAgreementChecked.observe(this@LoginByPwdActivityPopupActivity) {
                binding.agreementCheckbox.changeCheckState(it)
            }

            showAgreementDialog.observe(this@LoginByPwdActivityPopupActivity) { show: Boolean ->
                if (show) {
                    //显示协议弹窗
                    agreementDialog!!.safeShow()
                }
            }
        }
    }

}