package com.yzq.kotlincommon.ui.activity

import com.tencent.bugly.library.Bugly
import com.tencent.bugly.library.BuglyConstants
import com.therouter.router.Route
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityBuglyBinding

@Route(path = RoutePath.Main.BUGLY)
class BuglyActivity : BaseActivity() {
    private val binding by viewbind(ActivityBuglyBinding::inflate)


    override fun initListener() {
        binding.btnJavaCrash.setOnThrottleTimeClick {
            Bugly.testCrash(BuglyConstants.JAVA_CRASH)
        }

        binding.btnNativeCrash.setOnThrottleTimeClick {
            Bugly.testCrash(BuglyConstants.NATIVE_CRASH)
        }

        binding.btnAnr.setOnThrottleTimeClick {
            Bugly.testCrash(BuglyConstants.ANR_CRASH)
        }


    }
}