package com.yzq.login.ui.complete

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.CompleteRegisterInfoViewModel
import com.yzq.binding.viewBinding
import com.yzq.common.constants.RoutePath
import com.yzq.login.databinding.ActivityCompleteRegisterInfoBinding
import com.yzq.login.ui.BaseLoginActivity

/**
 *
 * @description: 完善注册信息
 * @author : yuzhiqiang
 *
 */
@Route(path = RoutePath.Login.COMPLETE_REGISTER_INFO)
class CompleteRegisterInfoActivity : BaseLoginActivity() {

    private val binding by viewBinding(
        ActivityCompleteRegisterInfoBinding::inflate
    )
    private val viewModel: CompleteRegisterInfoViewModel by viewModels()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CompleteRegisterInfoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initWidget() {
        binding.run {
            titleBackView.onBackIvClick {
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
            btnEnable.observe(this@CompleteRegisterInfoActivity) {
                binding.btnCompleteRegister.isEnabled = it
            }
        }
    }

}