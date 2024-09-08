package com.yzq.login.ui.immediate

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.CompleteRegisterInfoViewModel
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.login.databinding.ActivityCompleteRegisterInfoPopupBinding
import com.yzq.login.ui.BasePopupActivity
import floatWithSoftInput


/**
 * @description: 完善注册信息页面(半屏样式)
 * @author : yuzhiqiang
 */
@Route(path = RoutePath.Login.COMPLETE_REGISTER_INFO_POPUP)
class CompleteRegisterInfoPopupActivity : BasePopupActivity() {

    private val binding: ActivityCompleteRegisterInfoPopupBinding by viewbind(
        ActivityCompleteRegisterInfoPopupBinding::inflate
    )

    private val viewModel: CompleteRegisterInfoViewModel by viewModels()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CompleteRegisterInfoPopupActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun initWidget() {

        binding.run {

            floatWithSoftInput(bottomContent)

            popupHeader.onIvCloseClick {
                finish()
            }

            inputPwd.onContentChange {
                viewModel.changePwd(it)
            }

            btnCompleteRegister.setOnClickListener {
                viewModel.completeRegister()
            }
        }

    }

    override fun observeViewModel() {

        viewModel.run {
            btnEnable.observe(this@CompleteRegisterInfoPopupActivity) {
                binding.btnCompleteRegister.isEnabled = it
            }
        }
    }
}