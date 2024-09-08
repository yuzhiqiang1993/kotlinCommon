package com.yzq.login.ui.immediate

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.RetrievePwdViewModel
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.login.databinding.ActivityRetrievePwdPopupBinding
import com.yzq.login.ui.BasePopupActivity
import floatWithSoftInput


/**
 * @description: 忘记密码半屏页面
 * @author : yuzhiqiang
 */
@Route(path = RoutePath.Login.RETIREVE_PWD_POPUP)
class RetrievePwdPopupActivity : BasePopupActivity() {

    private val binding: ActivityRetrievePwdPopupBinding by viewbind(ActivityRetrievePwdPopupBinding::inflate)

    private val retrievePwdViewModel: RetrievePwdViewModel by viewModels()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RetrievePwdPopupActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun initListener() {
        binding.run {

            floatWithSoftInput(bottomContent)

            popupHeader.onIvCloseClick {
                finish()
            }


            /*手机号*/
            inputPhone.onContentChange {
                retrievePwdViewModel.changePhone(it)
            }


            //验证码
            inputSmsCode.run {
                onContentChange {
                    retrievePwdViewModel.changeSmsCode(it)
                }

            }

            inputSmsCode.run {
                onSmsBtnClick {
                    retrievePwdViewModel.sendSmsCode()
                }

                onContentChange {
                    retrievePwdViewModel.changeSmsCode(it)
                }

            }

            /*下一步*/
            btnNextStep.setOnClickListener {
                SetNewPwdPopupActivity.start(this@RetrievePwdPopupActivity)
                finish()
            }

        }
    }


    override fun observeViewModel() {
        retrievePwdViewModel.run {
            smsCodeBtnEnable.observe(this@RetrievePwdPopupActivity) {
                binding.inputSmsCode.changeSmsBtnEnable(it)
            }

            startCountDown.observe(this@RetrievePwdPopupActivity) {
                binding.inputSmsCode.startCountdown()
            }

            btnEnable.observe(this@RetrievePwdPopupActivity) {
                binding.btnNextStep.isEnabled = it
            }
        }

    }


}