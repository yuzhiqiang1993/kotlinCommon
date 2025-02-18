package com.yzq.kotlincommon.ui.activity

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.baidu.speech.EventListener
import com.baidu.speech.asr.SpeechConstant
import com.hjq.permissions.Permission
import com.therouter.router.Route
import com.xeon.asr_demo.ASRManager
import com.xeon.baidu.AutoCheck
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.core.extend.setOnThrottleTimeClick
import com.yzq.kotlincommon.databinding.ActivityAsrBinding
import com.yzq.logger.Logger
import com.yzq.permission.getPermissions
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar
import org.json.JSONObject


/**
 * @description 百度语音识别
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */
@Route(path = RoutePath.Main.ASR)
class AsrActivity : BaseActivity(), EventListener {


    private val stringBuilder = StringBuilder()

    private val binding by viewBinding(ActivityAsrBinding::inflate)

    override fun initWidget() {
        getPermissions(
            Permission.RECORD_AUDIO
        ) {

        }

        initToolbar(binding.includedToolbar.toolbar, "语音识别")

        ASRManager.instance.registerListener(this)
        binding.btnStart.setOnThrottleTimeClick {
            start()
        }
    }

    private fun start() {
        Logger.i("start")
        val param = ASRManager.getParam()
//        param.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, true)
//        param.put(SpeechConstant.ACCEPT_AUDIO_DATA, true)
//        param.put(SpeechConstant.DISABLE_PUNCTUATION, false)
//        param.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN)


        AutoCheck(applicationContext, object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                if (msg.what == 100) {
                    val autoCheck = msg.obj as AutoCheck
                    synchronized(autoCheck) {
                        val message =
                            autoCheck.obtainErrorMessage() // autoCheck.obtainAllMessage();
                        // 可以用下面一行替代，在logcat中查看代码
                        Logger.i("handleMessage: ${message}")
                    }
                }
            }
        }, false).checkAsr(param)

        val jsonParam = JSONObject(param.toMap()).toString()
        Logger.i("jsonParam:${jsonParam}")
        val event = SpeechConstant.ASR_START
        ASRManager.instance.send(event, jsonParam, null, 0, 0)
    }

    override fun onEvent(
        name: String?,
        params: String?,
        data: ByteArray?,
        offset: Int,
        length: Int
    ) {

        Logger.i(

            "onEvent: name:${name}},params:${params},data:${data},offset:${offset},length:${length}"
        )
        if (name != null) {
            when (name) {
                SpeechConstant.CALLBACK_EVENT_ASR_READY -> {
                    stringBuilder.appendLine("准备完毕，可以开始说话了")
                }

                SpeechConstant.CALLBACK_EVENT_ASR_BEGIN -> {
                    stringBuilder.appendLine("检测语音识别的起点")
                }

                SpeechConstant.CALLBACK_EVENT_ASR_VOLUME -> {
                    stringBuilder.appendLine("接收到的语音音量:${params}")
                }

                SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL -> {
                    stringBuilder.appendLine("部分语音识别结果:${params}")
                }

                SpeechConstant.CALLBACK_EVENT_ASR_END -> {
                    stringBuilder.appendLine("检测语音的重点：${params}")
                }

                SpeechConstant.CALLBACK_EVENT_ASR_FINISH -> {
                    /*语音识别完成，表示一句话已经说完。*/
                    stringBuilder.appendLine("语音识别完成：${params}")
                }

                SpeechConstant.CALLBACK_EVENT_ASR_EXIT -> {
                    stringBuilder.appendLine("语音识别引擎退出")
                }

                else -> {}


            }
        }
        binding.tvResult.text = stringBuilder.toString()

    }

    override fun onDestroy() {
        super.onDestroy()
        ASRManager.instance.send(SpeechConstant.ASR_STOP, null, null, 0, 0)
        ASRManager.instance.unregisterListener(this)
    }

}