package com.yzq.login.ui.immediate

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.AgreementViewModel
import com.yumc.android.userauth.login.view_model.OneKeyLoginViewModel
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.login.R
import com.yzq.login.databinding.ActivityLoginByOneKeyPopupBinding
import com.yzq.login.ui.BasePopupActivity
import com.yzq.login.ui.dialog.AgreementDialog


/**
 * @description: 一键登录半屏页面
 * @author : yuzhiqiang
 */

@Route(path = RoutePath.Login.ONE_KEY_LOGIN_POPUP)
class LoginByOneKeyPopupActivity : BasePopupActivity() {


    private val binding: ActivityLoginByOneKeyPopupBinding by viewbind(
        ActivityLoginByOneKeyPopupBinding::inflate
    )

    private val onKeyLoginViewModel: OneKeyLoginViewModel by viewModels()

    private val agreementViewModel: AgreementViewModel by viewModels()

    private var agreementDialog: AgreementDialog? = null


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginByOneKeyPopupActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun initWidget() {

        agreementDialog = AgreementDialog(this, R.string.one_key_login_agreement);
    }

    override fun initListener() {

        agreementDialog?.onBtnClick {
            onKeyLoginViewModel.agreementChecked(it)
            if (it) {
                onKeyLoginViewModel.onekeyLogin()
            }
        }

        binding.run {

            //点击浮层关闭
            main.setOnClickListener {
                finish()
            }

            //下面内容不做点击事件
            bottomContent.setOnClickListener(null)

            popupHeader.onIvCloseClick {
                finish()
            }

            //当前手机号
            tvPhone.setText("159xxxxx1234")

            //当前手机号登录
            btnCurrentPhoneLogin.setOnClickListener {
                onKeyLoginViewModel.onekeyLogin()
            }

            //换号码
            tvChangePhone.setOnClickListener {
                //跳转到验证码登录页面
                finish()
                LoginByPwdActivityPopupActivity.start(this@LoginByOneKeyPopupActivity)

            }

            agreementCheckboxView.run {
                //复选框
                onAgreementChecked {
                    onKeyLoginViewModel.agreementChecked(it)
                }
                //用户协议
                onAgreementClick {
                    agreementViewModel.agreementClick(it)
                }


            }


        }
    }


    override fun observeViewModel() {


        onKeyLoginViewModel.run {
            isAgreementChecked.observe(this@LoginByOneKeyPopupActivity) {
                binding.agreementCheckboxView.changeCheckState(it)
            }

            showAgreementDialog.observe(this@LoginByOneKeyPopupActivity) {
                if (it) {
                    agreementDialog?.safeShow()
                }
            }

        }

    }

}