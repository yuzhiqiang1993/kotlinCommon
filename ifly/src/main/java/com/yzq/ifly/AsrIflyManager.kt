package com.yzq.ifly

import com.iflytek.cloud.ErrorCode
import com.iflytek.cloud.InitListener
import com.iflytek.cloud.RecognizerListener
import com.iflytek.cloud.RecognizerResult
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechRecognizer
import com.iflytek.cloud.SpeechUtility
import com.yzq.application.AppManager
import com.yzq.logger.Logger
import org.json.JSONObject
import java.util.concurrent.atomic.AtomicBoolean


/**
 * @description: 讯飞语音识别管理类
 * @author : yuzhiqiang
 */

object AsrIflyManager {

    const val TAG = "AsrIflyManager"

    private val initialized = AtomicBoolean(false)


    private var speechRecognizer: SpeechRecognizer? = null

    fun init(appId: String) {
        if (initialized.compareAndSet(false, true)) {
            SpeechUtility.createUtility(AppManager.application, SpeechConstant.APPID + "=${appId}");
        }

    }

    fun startRecognition(listener: RecognizerListener) {
        if (initialized.get()) {
            Logger.it(TAG, "开始识别")
            createSpeechRecognizer()
            speechRecognizer?.startListening(listener)
        } else {
            Logger.et(TAG, "未初始化")
        }

    }

    private val initListener = InitListener { code ->
        if (code != ErrorCode.SUCCESS) {
            Logger.et(
                TAG,
                "初始化失败,错误码：$code,请点击网址https://www.xfyun.cn/document/error-code查询解决方案"
            );
        } else {
            Logger.it(TAG, "初始化成功")
        }

    }

    private fun createSpeechRecognizer() {

        if (speechRecognizer == null) {
            this.speechRecognizer = SpeechRecognizer.createRecognizer(
                AppManager.application, initListener
            ).apply {
                setParameter(SpeechConstant.PARAMS, null);
                setParameter("dwa", "wpgs")//开启动态修正
                setParameter(SpeechConstant.RESULT_TYPE, "json")// 设置返回结果格式
                setParameter(SpeechConstant.LANGUAGE, "zh_cn")// 设置语音输入语言，zh_cn为简体中文
                // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
                setParameter(SpeechConstant.VAD_BOS, "4000")

                // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
                setParameter(SpeechConstant.VAD_EOS, "5000")

                // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
                setParameter(SpeechConstant.ASR_PTT, "1")

                // 设置音频保存路径，保存音频格式支持 pcm、wav.
                setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
//                setParameter(
//                    SpeechConstant.ASR_AUDIO_PATH,
//                    getExternalFilesDir("msc")?.absolutePath + "/iat.wav"
//                )
            }
        } else {
            Logger.it(TAG, "已经创建过 speechRecognizer")
        }

    }

    fun stopRecognition() {
        speechRecognizer?.stopListening()
    }

    fun cancleRecognition() {
        speechRecognizer?.cancel()
    }

    fun destroyRecognition() {
        cancleRecognition()
        speechRecognizer?.destroy()
    }


    fun handleSpeechResult(result: RecognizerResult): String = kotlin.runCatching {
        val jsonResult = JSONObject(result.resultString)
        // 提取识别出的文本
        val wsArray = jsonResult.optJSONArray("ws") // "ws" 是分词的数组
        val recognizedWords = StringBuilder()
        // 拼接识别出的每个词
        if (wsArray != null) {
            for (i in 0 until wsArray.length()) {
                val wordObj = wsArray.getJSONObject(i)
                val cwArray = wordObj.optJSONArray("cw") // "cw" 是词语和得分
                if (cwArray != null && cwArray.length() > 0) {
                    val word = cwArray.getJSONObject(0).optString("w")//w 是词语
                    recognizedWords.append(word)
                }
            }
        }

        return recognizedWords.toString()

    }.getOrDefault("")


}
