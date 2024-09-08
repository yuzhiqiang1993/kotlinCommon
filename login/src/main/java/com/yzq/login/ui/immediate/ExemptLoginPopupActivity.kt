package com.yzq.login.ui.immediate

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.ExemptLoginViewModel
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.login.databinding.ActivityExemptLoginPopupBinding
import com.yzq.login.ui.BasePopupActivity


/**
 * @description: 免登录
 * @author : yuzhiqiang
 */
@Route(path = RoutePath.Login.EXEMPT_LOGIN_POPUP)
class ExemptLoginPopupActivity : BasePopupActivity() {

    private val binding: ActivityExemptLoginPopupBinding by viewbind(ActivityExemptLoginPopupBinding::inflate)

    private val viewModel: ExemptLoginViewModel by viewModels()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ExemptLoginPopupActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initWidget() {
        binding.run {
            main.setOnClickListener { finish() }
            bottomContent.setOnClickListener(null)

            popupHeader.onIvCloseClick {
                finish()
            }

        }

    }


}