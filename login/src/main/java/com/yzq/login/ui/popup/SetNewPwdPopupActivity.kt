package com.yzq.login.ui.popup

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.therouter.TheRouter
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.SetNewPwdViewModel
import com.yzq.base.extend.navFinish
import com.yzq.binding.viewBinding
import com.yzq.common.constants.RoutePath
import com.yzq.login.databinding.ActivitySetNewPwdPopupBinding
import com.yzq.login.manager.PageManager
import com.yzq.login.ui.BasePopupActivity
import com.yzq.softinput.setWindowSoftInput


/**
 * @description: 设置新密码页面
 * @author : yuzhiqiang
 */
@Route(path = RoutePath.Login.SET_NEW_PWD_POPUP)
class SetNewPwdPopupActivity : BasePopupActivity() {

    private val binding by viewBinding(ActivitySetNewPwdPopupBinding::inflate)

    private val viewModel: SetNewPwdViewModel by viewModels()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SetNewPwdPopupActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun initWidget() {

        binding.run {
            bottomSheetView = bottomContent
            popupHeader.showBack(true)
        }
    }

    override fun initListener() {
        binding.run {

            setWindowSoftInput(bottomContent)


            popupHeader.onIvBackClick {
                handleBackPressed()
            }
            popupHeader.onIvCloseClick {
                PageManager.finishAll()
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


    override fun handleBackPressed() {
        TheRouter.build(RoutePath.Login.RETIREVE_PWD_POPUP)
            .navFinish(this)
    }
}