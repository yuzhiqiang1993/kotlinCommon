package com.yzq.login.ui.immediate

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.SetNewPwdViewModel
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.login.databinding.ActivitySetNewPwdPopupBinding
import com.yzq.login.ui.BasePopupActivity
import floatWithSoftInput


/**
 * @description: 设置新密码页面
 * @author : yuzhiqiang
 */
@Route(path = RoutePath.Login.SET_NEW_PWD_POPUP)
class SetNewPwdPopupActivity : BasePopupActivity() {

    private val binding: ActivitySetNewPwdPopupBinding by viewbind(ActivitySetNewPwdPopupBinding::inflate)

    private val viewModel: SetNewPwdViewModel by viewModels()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SetNewPwdPopupActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun initListener() {
        binding.run {

            floatWithSoftInput(bottomContent)

            popupHeader.onIvCloseClick {
                finish()
            }

            inputPwd.onContentChange {
                viewModel.changePwd(it)
            }

            btnLogin.setOnClickListener {
                //设置新密码
                viewModel.setNewPwd()
            }
        }
    }


    override fun observeViewModel() {

        viewModel.run {
            btnEnable.observe(this@SetNewPwdPopupActivity) {
                binding.btnLogin.isEnabled = it
            }
        }
    }

}