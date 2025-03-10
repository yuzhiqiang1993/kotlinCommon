package com.yzq.kotlincommon.ui.activity

import android.graphics.Color
import android.text.TextUtils
import androidx.activity.viewModels
import com.therouter.router.Route
import com.yzq.baseui.BaseActivity
import com.yzq.binding.dataBinding
import com.yzq.core.extend.setOnThrottleTimeClick
import com.yzq.core.observeUIState
import com.yzq.coroutine.interval.interval
import com.yzq.dialog.BubbleLoadingDialog
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityLoginBinding
import com.yzq.kotlincommon.view_model.LoginViewModel
import com.yzq.logger.Logger
import com.yzq.router.RoutePath
import com.yzq.router.navClear
import com.yzq.storage.mmkv.MMKVDefault
import com.yzq.storage.mmkv.MMKVUser
import com.yzq.util.ext.immersive

/**
 * @description: SharedPreference相关
 * @author : yzq
 * @date : 2019/4/30
 * @time : 13:39
 *
 */

@Route(path = RoutePath.Main.LOGIN)
class LoginActivity : BaseActivity() {

    private val binding by dataBinding<ActivityLoginBinding>(R.layout.activity_login)
    private val vm: LoginViewModel by viewModels()
    private val interval = interval()
    private val loadingDialog by lazy { BubbleLoadingDialog(this) }

    override fun initWidget() {
        super.initWidget()


        immersive(Color.WHITE, true)

        /*开启一个页面级别的轮询*/
        interval
            .onlyResumed(this)//onpause 暂停  onResume恢复
            .subscribe {
                Logger.i("第:${it} 的轮询")
            }.start()


        binding.tvClearMmkv.setOnThrottleTimeClick {
            MMKVDefault.mmkv.clearAll()
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

        Logger.i("initData")
        Logger.i("MMKVUtil.account = ${MMKVUser.account}")
        Logger.i("MMKVUtil.pwd = ${MMKVUser.pwd}")

        binding.account = MMKVUser.account
        binding.pwd = MMKVUser.pwd
    }

    override fun observeViewModel() {

        vm.run {
            observeUIState(this, loadingDialog)
            loginLiveData.observe(this@LoginActivity) {
                MMKVUser.hasLogin = true
                navClear(RoutePath.Main.MAIN)

//                navFinish(RoutePath.Main.MAIN)
            }
        }
    }

}
