package com.yzq.aliemas.crash

import com.therouter.router.Route
import com.yzq.aliemas.databinding.ActivityCrashBinding
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.core.extend.setOnThrottleTimeClick
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar


@Route(path = RoutePath.Emas.CRASH)
class CrashActivity : BaseActivity() {

    private val binding by viewBinding(ActivityCrashBinding::inflate)


    override fun initWidget() {
        initToolbar(binding.includedToolbar.toolbar, "Crash")

        binding.btnJavaCrash.setOnThrottleTimeClick {
            throw NullPointerException("java crash")
        }

        binding.btnNativeCrash.setOnThrottleTimeClick {


        }

        binding.btnAnr.setOnThrottleTimeClick {
            Thread.sleep(100000)
        }
    }

}