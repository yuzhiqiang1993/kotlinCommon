package com.yzq.login.ui.popup

import androidx.activity.viewModels
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.CompleteRegisterInfoViewModel
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.login.databinding.ActivityCompleteRegisterInfoPopupBinding
import com.yzq.login.manager.PageManager
import com.yzq.login.ui.BasePopupActivity
import com.yzq.softinput.setWindowSoftInput


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


    override fun initWidget() {

        binding.run {
            bottomSheetView = bottomContent
            setWindowSoftInput(bottomContent)
        }

    }

    override fun initListener() {
        binding.run {
            popupHeader.onIvCloseClick {
                PageManager.finishAll()
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

    override fun handleBackPressed() {
        PageManager.finishAll()
    }
}