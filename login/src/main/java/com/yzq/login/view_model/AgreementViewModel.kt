package com.yumc.android.userauth.login.view_model


import com.yzq.base.view_model.BaseViewModel
import com.yzq.logger.Logger


/**
 *
 * @description: 协议相关的ViewModel
 * @author : yuzhiqiang
 *
 */

class AgreementViewModel : BaseViewModel() {

    fun agreementClick(content: String) {
        when (content.trim()) {
            "《中国移动认证服务协议》" -> {
                Logger.it(TAG, "中国移动认证服务协议")
            }

            "《会员协议》" -> {
                Logger.it(TAG, "会员协议")
            }

            "《隐私条款》" -> {
                Logger.it(TAG, "隐私条款")

            }

            "《App会员条款》" -> {
                Logger.it(TAG, "App会员条款")
            }

            else -> {}
        }

    }


}