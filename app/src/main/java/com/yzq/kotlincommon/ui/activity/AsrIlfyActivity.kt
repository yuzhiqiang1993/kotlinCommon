package com.yzq.kotlincommon.ui.activity

import android.os.Bundle
import com.hjq.permissions.Permission
import com.iflytek.cloud.RecognizerListener
import com.iflytek.cloud.RecognizerResult
import com.iflytek.cloud.SpeechError
import com.therouter.router.Route
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.core.extend.setOnThrottleTimeClick
import com.yzq.ifly.asr.AsrIflyManager
import com.yzq.kotlincommon.databinding.ActivityAsrBinding
import com.yzq.logger.Logger
import com.yzq.permission.getPermissions
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar


/**
 * @description: 讯飞页面
 *
 * @author : yuzhiqiang
 */

@Route(path = RoutePath.Main.ASR_IFLY)
class AsrIlfyActivity : BaseActivity() {

    private val stringBuilder = StringBuilder()

    private val binding by viewBinding(ActivityAsrBinding::inflate)

    private val recognizerListener = object : RecognizerListener {
        override fun onVolumeChanged(volume: Int, data: ByteArray?) {
            Logger.it(TAG, "音量发生变化:$volume, data:${data?.contentToString()}")
//            stringBuilder.appendLine("音量发生变化:$volume}").appendLine()

        }

        override fun onBeginOfSpeech() {
            Logger.it(TAG, "开始说话")
            stringBuilder.appendLine("开始说话").appendLine()
            binding.tvResult.text = stringBuilder.toString()
        }

        override fun onEndOfSpeech() {
            Logger.it(TAG, "结束说话")
            stringBuilder.appendLine("结束说话").appendLine()
            binding.tvResult.text = stringBuilder.toString()
        }

        override fun onResult(result: RecognizerResult?, isLast: Boolean) {
            result?.run {
                Logger.it(TAG, "识别结果:${result.resultString} , 是否最后一行:${isLast}")
                val text = AsrIflyManager.handleSpeechResult(result)
                stringBuilder.appendLine("识别结果:$text, 是否最后一行:${isLast}").appendLine()
                binding.tvResult.text = stringBuilder.toString()
            }
        }

        override fun onError(speechError: SpeechError?) {
            Logger.it(TAG, "识别错误:${speechError?.errorDescription}")
            stringBuilder.appendLine("识别错误:${speechError?.errorDescription}").appendLine()
            binding.tvResult.text = stringBuilder.toString()
        }

        override fun onEvent(eventType: Int, arg1: Int, arg2: Int, obj: Bundle?) {
//            Logger.it(TAG, "onEvent, eventType:$eventType, arg1:$arg1, arg2:$arg2, obj:$obj")
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }

    }

    override fun initWidget() {

        initToolbar(binding.includedToolbar.toolbar, "讯飞语音识别")

        binding.btnStart.setOnThrottleTimeClick {
            getPermissions(
                Permission.RECORD_AUDIO
            ) {
                AsrIflyManager.startRecognition(recognizerListener)
            }

        }

        binding.btnStop.setOnThrottleTimeClick {
            AsrIflyManager.stopRecognition()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        AsrIflyManager.destroyRecognition()
    }
}