package com.yzq.kotlincommon.ui.activity

import com.baidu.speech.EventListener
import com.baidu.speech.asr.SpeechConstant
import com.hjq.permissions.Permission
import com.therouter.router.Route
import com.yzq.baidu.asr.ASRBaiduManager
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.core.extend.setOnThrottleTimeClick
import com.yzq.kotlincommon.databinding.ActivityAsrBinding
import com.yzq.logger.Logger
import com.yzq.permission.getPermissions
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar


/**
 * @description 百度语音识别
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */
@Route(path = RoutePath.Main.ASR_BAIDU)
class AsrBaiduActivity : BaseActivity(), EventListener {


    private val stringBuilder = StringBuilder()

    private val binding by viewBinding(ActivityAsrBinding::inflate)


    override fun initWidget() {


        initToolbar(binding.includedToolbar.toolbar, "百度语音识别")

        binding.btnStart.setOnThrottleTimeClick {
            getPermissions(
                Permission.RECORD_AUDIO
            ) {
                ASRBaiduManager.startRecognition(this)
            }

        }

        binding.btnStop.setOnThrottleTimeClick {
            ASRBaiduManager.stopRecognition()
        }
    }

    override fun onEvent(
        name: String?, params: String?, data: ByteArray?, offset: Int, length: Int
    ) {

//        Logger.it(
//            TAG,
//            "onEvent: name:${name}},params:${params},data:${data},offset:${offset},length:${length}"
//        )
        if (name != null) {
            when (name) {
                SpeechConstant.CALLBACK_EVENT_ASR_READY -> {
                    stringBuilder.appendLine("准备完毕，可以开始说话了").appendLine()
                }

                SpeechConstant.CALLBACK_EVENT_ASR_BEGIN -> {
                    stringBuilder.appendLine("检测语音识别的起点").appendLine()
                }

                SpeechConstant.CALLBACK_EVENT_ASR_VOLUME -> {
                    stringBuilder.appendLine("接收到的语音音量:${params}").appendLine()
                }

                SpeechConstant.CALLBACK_EVENT_ASR_AUDIO -> {
//                    stringBuilder.appendLine("音频数据：${data?.contentToString()}").appendLine()
                    //打印出 bytearray
                    Logger.it(TAG, "音频数据：${data?.contentToString()}")
                }

                SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL -> {
                    stringBuilder.appendLine("部分语音识别结果:${params}").appendLine()
                }

                SpeechConstant.CALLBACK_EVENT_ASR_END -> {
                    stringBuilder.appendLine("检测语音的重点：${params}").appendLine()
                }

                SpeechConstant.CALLBACK_EVENT_ASR_FINISH -> {
                    stringBuilder.appendLine("语音识别结束：${params}").appendLine()
                }

                SpeechConstant.CALLBACK_EVENT_ASR_EXIT -> {
                    stringBuilder.appendLine("语音识别引擎退出").appendLine()
                }


                else -> {}


            }
        }
        binding.tvResult.text = stringBuilder.toString()

    }

    override fun onDestroy() {
        super.onDestroy()
        ASRBaiduManager.destroyRecognition()
    }

}