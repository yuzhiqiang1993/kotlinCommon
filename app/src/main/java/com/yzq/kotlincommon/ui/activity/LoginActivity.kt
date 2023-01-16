package com.yzq.kotlincommon.ui.activity

import android.graphics.Color
import android.text.TextUtils
import androidx.activity.viewModels
import com.blankj.utilcode.util.LogUtils
import com.therouter.router.Route
import com.yzq.base.extend.navClear
import com.yzq.base.extend.observeUIState
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.databind
import com.yzq.common.constants.RoutePath
import com.yzq.common.utils.MMKVUtil
import com.yzq.coroutine.interval.Interval
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityLoginBinding
import com.yzq.kotlincommon.view_model.LoginViewModel
import com.yzq.statusbar.immersive
import java.util.concurrent.TimeUnit

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

    private val interval = Interval(2, TimeUnit.SECONDS, 0)

    override fun initWidget() {
        super.initWidget()

        immersive(Color.WHITE, true)

        /*开启一个页面级别的轮询*/
        interval.onlyResumed(this)//onpause 暂停  onResume恢复
            .subscribe {
                LogUtils.i("第:${it} 的轮询")
            }.start()


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

        vm.run {
            observeUIState(this, loadingDialog)
            loginLiveData.observe(this@LoginActivity) {
                MMKVUtil.hasLogin = true
                navClear(RoutePath.Main.MAIN)

//                navFinish(RoutePath.Main.MAIN)
            }
        }
    }

}
