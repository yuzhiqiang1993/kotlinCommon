package com.yzq.kotlincommon.ui.activity

import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.yzq.base.extend.nav
import com.yzq.base.ui.activity.BaseVmActivity
import com.yzq.binding.databind
import com.yzq.common.constants.RoutePath
import com.yzq.common.utils.MMKVUtil
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityLoginBinding
import com.yzq.kotlincommon.view_model.LoginViewModel

/**
 * @description: SharedPreference相关
 * @author : yzq
 * @date : 2019/4/30
 * @time : 13:39
 *
 */

@Route(path = RoutePath.Main.LOGIN)
class LoginActivity : BaseVmActivity<LoginViewModel>() {

    private val binding by databind<ActivityLoginBinding>(R.layout.activity_login)

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun initWidget() {
        super.initWidget()

        colorStatusBar(com.yzq.resource.R.color.white, binding.layoutContainer, true)

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

        LogUtils.i("initData")
        LogUtils.i("MMKVUtil.account = ${MMKVUtil.account}")
        LogUtils.i("MMKVUtil.pwd = ${MMKVUtil.pwd}")

        binding.account = MMKVUtil.account
        binding.pwd = MMKVUtil.pwd
    }

    override fun observeViewModel() {

        vm.run {
            loginLiveData.observe(this@LoginActivity) {
                nav(RoutePath.Main.MAIN)
            }
        }
    }
}
