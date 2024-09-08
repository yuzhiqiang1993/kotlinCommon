package com.yzq.kotlincommon.ui.activity

import com.therouter.TheRouter
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityUserAuthBinding


/**
 * @description: 用户认证页面
 * @author : yuzhiqiang
 */
@Route(path = RoutePath.Main.USER_AUTH)
class UserAuthActivity : BaseActivity() {

    private val binding: ActivityUserAuthBinding by viewbind(ActivityUserAuthBinding::inflate)


    override fun initWidget() {

        binding.run {

            initToolbar(toolbar = includedToolbar.toolbar, title = "用户认证")

            btnLoginByOnkey.setOnThrottleTimeClick {
                TheRouter.build(RoutePath.Login.ONE_KEY_LOGIN).navigation()
            }

            btnLoginByOnekeyPopup.setOnThrottleTimeClick {
                TheRouter.build(RoutePath.Login.ONE_KEY_LOGIN_POPUP).navigation()
            }

            btnExemptLogin.setOnThrottleTimeClick {
                TheRouter.build(RoutePath.Login.EXEMPT_LOGIN_POPUP).navigation()
            }

            btnCompleteRegisterInfo.setOnThrottleTimeClick {
                TheRouter.build(RoutePath.Login.COMPLETE_REGISTER_INFO).navigation()
            }
            btnCompleteRegisterInfoPopup.setOnThrottleTimeClick {
                TheRouter.build(RoutePath.Login.COMPLETE_REGISTER_INFO_POPUP).navigation()
            }
        }


    }

}