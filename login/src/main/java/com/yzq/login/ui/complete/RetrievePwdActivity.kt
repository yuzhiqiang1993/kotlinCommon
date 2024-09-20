package com.yzq.login.ui.complete

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.therouter.router.Route
import com.yumc.android.userauth.login.view_model.RetrievePwdViewModel
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.login.databinding.ActivityRetrievePwdBinding
import com.yzq.login.ui.BaseLoginActivity


/**
 *
 * @description: 找回密码
 * @author : yuzhiqiang
 *
 */
@Route(path = RoutePath.Login.RETIREVE_PWD)
class RetrievePwdActivity : BaseLoginActivity() {
    private val binding: ActivityRetrievePwdBinding by viewbind(ActivityRetrievePwdBinding::inflate)

    private val retrievePwdViewModel: RetrievePwdViewModel by viewModels()


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RetrievePwdActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun initListener() {

        binding.run {

            titleBackView.onBackIvClick {
                finish()
            }


            /*手机号*/
            inputPhone.onContentChange {
                retrievePwdViewModel.changePhone(it)
            }

            //验证码
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

            SetNewPwdActivity.start(this@RetrievePwdActivity)
                finish()
            }

        }
    }


    override fun observeViewModel() {
        retrievePwdViewModel.run {
            smsCodeBtnEnable.observe(this@RetrievePwdActivity) {
                binding.inputSmsCode.changeSmsBtnEnable(it)
            }

            startCountDown.observe(this@RetrievePwdActivity) {
                binding.inputSmsCode.startCountdown()
            }

            btnEnable.observe(this@RetrievePwdActivity) {
                binding.btnNextStep.isEnabled = it
            }
        }

    }


}