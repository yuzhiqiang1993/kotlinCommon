package com.yzq.login.ui.popup

import androidx.activity.viewModels
import com.therouter.TheRouter
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.AgreementViewModel
import com.yzq.binding.viewBinding
import com.yzq.login.R
import com.yzq.login.databinding.ActivityLoginByPwdPopupBinding
import com.yzq.login.manager.PageManager
import com.yzq.login.ui.BasePopupActivity
import com.yzq.login.ui.dialog.AgreementDialog
import com.yzq.login.view_model.LoginPwdViewModel
import com.yzq.router.RoutePath
import com.yzq.router.navFinish
import com.yzq.softinput.setWindowSoftInput


/**
 * @description: 密码登录半屏页面
 * @author : yuzhiqiang
 */
@Route(path = RoutePath.Login.LOGIN_BY_PWD_POPUP)
class LoginByPwdPopupActivity : BasePopupActivity() {

    private val binding: ActivityLoginByPwdPopupBinding by viewBinding(
        ActivityLoginByPwdPopupBinding::inflate
    )

    private val loginByPwdViewModel: LoginPwdViewModel by viewModels()

    private val agreementViewModel: AgreementViewModel by viewModels()

    private var agreementDialog: AgreementDialog? = null

    override fun initWidget() {

        binding.run {
            //设置指定View浮动在软键盘上方
            setWindowSoftInput(bottomContent)
            popupHeader.showBack(true)
            bottomSheetView = bottomContent
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
                handleBackPressed()
            }

            popupHeader.onIvCloseClick {
                //返回到菜单
                PageManager.finishAll()
            }

            popupTitle.titleEndOnClick {
                //验证码登录
                TheRouter.build(RoutePath.Login.LOGIN_BY_SMS_POPUP)
                    .navFinish(this@LoginByPwdPopupActivity)

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
                RetrievePwdPopupActivity.start(this@LoginByPwdPopupActivity)
                finish()
            }
        }

    }


    override fun observeViewModel() {

        loginByPwdViewModel.run {
            btnEnable.observe(this@LoginByPwdPopupActivity) { enable: Boolean? ->
                binding.btnLogin.isEnabled = enable!!
            }

            isAgreementChecked.observe(this@LoginByPwdPopupActivity) {
                binding.agreementCheckbox.changeCheckState(it)
            }

            showAgreementDialog.observe(this@LoginByPwdPopupActivity) { show: Boolean ->
                if (show) {
                    //显示协议弹窗
                    agreementDialog!!.safeShow()
                }
            }
        }
    }

    override fun handleBackPressed() {
        //返回到验证码登录页面
        TheRouter.build(RoutePath.Login.LOGIN_BY_SMS_POPUP)
            .navFinish(this@LoginByPwdPopupActivity)
    }

}