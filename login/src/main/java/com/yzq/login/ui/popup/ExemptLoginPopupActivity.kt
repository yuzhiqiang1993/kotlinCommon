package com.yzq.login.ui.popup

import androidx.activity.viewModels
import com.therouter.TheRouter
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.ExemptLoginViewModel
import com.yzq.base.extend.navFinish
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.login.databinding.ActivityExemptLoginPopupBinding
import com.yzq.login.manager.PageManager
import com.yzq.login.ui.BasePopupActivity


/**
 * @description: 免登录
 * @author : yuzhiqiang
 */
@Route(path = RoutePath.Login.EXEMPT_LOGIN_POPUP)
class ExemptLoginPopupActivity : BasePopupActivity() {

    private val binding: ActivityExemptLoginPopupBinding by viewbind(ActivityExemptLoginPopupBinding::inflate)

    private val viewModel: ExemptLoginViewModel by viewModels()


    override fun initWidget() {
        binding.run {
            bottomSheetView = bottomContent
        }
    }


    override fun initListener() {
        binding.run {
            main.setOnClickListener { PageManager.finishAll() }
            bottomContent.setOnClickListener(null)

            popupHeader.onIvCloseClick {
                PageManager.finishAll()
            }

            //本机号码一键登录
            btnCurrentPhoneLogin.setOnThrottleTimeClick {
                viewModel.oneKeyLogin()
            }

            //换号
            tvChangePhone.setOnThrottleTimeClick {
                TheRouter.build(RoutePath.Login.LOGIN_BY_SMS_POPUP)
                    .navFinish(this@ExemptLoginPopupActivity)
            }

            //手机验证码登录
            btnExemptLogin.setOnThrottleTimeClick {
                viewModel.exemptLogin()
            }


        }
    }


    override fun handleBackPressed() {
        PageManager.finishAll()
    }


}