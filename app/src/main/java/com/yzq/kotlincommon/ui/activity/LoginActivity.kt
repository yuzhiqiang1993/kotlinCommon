package com.yzq.kotlincommon.ui.activity

import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import androidx.activity.viewModels
import com.blankj.utilcode.util.LogUtils
import com.therouter.TheRouter
import com.therouter.router.Route
import com.yzq.base.extend.observeUIState
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.databind
import com.yzq.common.constants.RoutePath
import com.yzq.common.utils.MMKVUtil
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityLoginBinding
import com.yzq.kotlincommon.view_model.LoginViewModel
import com.yzq.statusbar.immersive
import com.yzq.widget.dialog.BubbleDialog

/**
 * @description: SharedPreference相关
 * @author : yzq
 * @date : 2019/4/30
 * @time : 13:39
 *
 */

@Route(path = RoutePath.Main.LOGIN)
class LoginActivity : BaseActivity() {

    private val binding by databind<ActivityLoginBinding>(R.layout.activity_login)

    private val vm: LoginViewModel by viewModels()


    private val bubbleLoadingDialog by lazy { BubbleDialog(this) }

    override fun initWidget() {
        super.initWidget()

        immersive(Color.WHITE, true)

        binding.tvClearMmkv.setOnThrottleTimeClick {
            MMKVUtil.clear()
            initData()
        }

        binding.btnLogin.setOnThrottleTimeClick {

            if (TextUtils.isEmpty(binding.account)) {
                binding.inputLayoutAccount.error = "账号不能为空，请检查"
            }
            if (TextUtils.isEmpty(binding.pwd)) {

                binding.inputLayoutPwd.error = "密码不能为空，请检查"
            }

            if (TextUtils.isEmpty(binding.account) or TextUtils.isEmpty(binding.pwd)) {
                return@setOnThrottleTimeClick
            }

            vm.login(binding.account!!, binding.pwd!!)
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
        observeUIState(vm, bubbleLoadingDialog)
        vm.run {
            loginLiveData.observe(this@LoginActivity) {
                TheRouter.build(RoutePath.Main.MAIN)
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    .navigation()
                finish()
            }
        }
    }
}
