package com.yzq.login.ui.complete

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.SetNewPwdViewModel
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.login.databinding.ActivitySetNewPwdBinding
import com.yzq.login.ui.BaseCompleteActivity


/**
 *
 * @description: 设置新密码页面(找回密码2)
 * @author : yuzhiqiang
 *
 */

@Route(path = RoutePath.Login.SET_NEW_PWD)
class SetNewPwdActivity : BaseCompleteActivity() {

    private val binding: ActivitySetNewPwdBinding by viewbind(ActivitySetNewPwdBinding::inflate)

    private val viewModel: SetNewPwdViewModel by viewModels()


    companion object {

        fun start(context: Context) {
            val intent = Intent(context, SetNewPwdActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun initListener() {
        binding.run {
            titleBackView.onBackIvClick {
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
            btnEnable.observe(this@SetNewPwdActivity) {
                binding.btnLogin.isEnabled = it
            }
        }
    }

}