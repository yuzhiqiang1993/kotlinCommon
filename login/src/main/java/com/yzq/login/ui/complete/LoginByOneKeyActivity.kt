package com.yzq.login.ui.complete

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.therouter.TheRouter
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.AgreementViewModel
import com.yumc.android.userauth.login.view_model.OneKeyLoginViewModel
import com.yzq.binding.viewBinding
import com.yzq.login.R
import com.yzq.login.databinding.ActivityLoginByOneKeyBinding
import com.yzq.login.ui.BaseLoginActivity
import com.yzq.login.ui.dialog.AgreementDialog
import com.yzq.router.RoutePath
import com.yzq.router.navFinish


/**
 *
 * @description:一键登录页面
 * @author : yuzhiqiang
 *
 */

@Route(path = RoutePath.Login.ONE_KEY_LOGIN)
class LoginByOneKeyActivity : BaseLoginActivity() {

    private val binding by viewBinding(ActivityLoginByOneKeyBinding::inflate)

    private val onKeyLoginViewModel: OneKeyLoginViewModel by viewModels()

    private val agreementViewModel: AgreementViewModel by viewModels()

    private var agreementDialog: AgreementDialog? = null


    companion object {

        fun start(context: Context) {
            val intent = Intent(context, LoginByOneKeyActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initWidget() {
        super.initWidget()
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
            //关闭页面
            ivClose.setOnClickListener { finish() }

            //当前手机号
            tvPhone.setText("159xxxxx1234")

            //当前手机号登录
            btnCurrentPhoneLogin.setOnClickListener {
                onKeyLoginViewModel.onekeyLogin()
            }

            //其他手机号登录
            btnOtherPhoneLogin.setOnClickListener {
                //跳转到验证码登录页面
                TheRouter.build(RoutePath.Login.LOGIN_BY_SMS).navFinish(this@LoginByOneKeyActivity)
                finish()

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

            isAgreementChecked.observe(this@LoginByOneKeyActivity) {
                binding.agreementCheckboxView.changeCheckState(it)
            }

            showAgreementDialog.observe(this@LoginByOneKeyActivity) {
                if (it) {
                    agreementDialog?.safeShow()
                } else {
                    agreementDialog?.safeDismiss()
                }
            }
        }
    }

}