package com.yzq.kotlincommon.ui.activity

import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.constants.RoutePath
import com.yzq.common.utils.LocalSpUtils
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityLoginBinding
import com.yzq.kotlincommon.mvvm.view_model.LoginViewModel
import com.yzq.lib_base.extend.nav
import com.yzq.lib_base.ui.activity.BaseDbVmActivity


/**
 * @description: SharedPreference相关
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:39
 *
 */

@Route(path = RoutePath.Main.LOGIN)
class LoginActivity : BaseDbVmActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun getContentLayoutId() = R.layout.activity_login

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java


    override fun initWidget() {
        super.initWidget()

        colorStatusBar(R.color.white, binding.layoutContainer, true)

        binding.btnLogin.setOnClickListener {


            if (TextUtils.isEmpty(binding.account)) {
                binding.inputLayoutAccount.error = "账号不能为空，请检查"

            }
            if (TextUtils.isEmpty(binding.pwd)) {

                binding.inputLayoutPwd.error = "密码不能为空，请检查"

            }

            if (TextUtils.isEmpty(binding.account) or TextUtils.isEmpty(binding.pwd)) {
                return@setOnClickListener
            }


            vm.login(
                binding.account!!,
                binding.pwd!!
            )


        }
    }

    override fun initData() {

        binding.account = LocalSpUtils.account
        binding.pwd = LocalSpUtils.pwd

    }


    override fun observeViewModel() {

        with(vm) {
            loginLiveData.observe(this@LoginActivity) {
                nav(RoutePath.Main.MAIN)
            }
        }
    }


}
