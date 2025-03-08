package com.yzq.ali.asr

import android.Manifest
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.annotation.RequiresPermission
import com.alibaba.idst.nui.AsrResult
import com.alibaba.idst.nui.CommonUtils
import com.alibaba.idst.nui.Constants
import com.alibaba.idst.nui.INativeNuiCallback
import com.alibaba.idst.nui.KwsResult
import com.alibaba.idst.nui.NativeNui
import com.yzq.application.AppContext
import com.yzq.application.AppStorage
import com.yzq.logger.Logger
import org.json.JSONObject
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean


/**
 * @description: 阿里语音识别管理类
 * @author : yuzhiqiang
 */

object AsrAliManager {

    private const val TAG = "AliAsrManager"

    private val nuiInstance by lazy { NativeNui() }
    private var audioRecorder: AudioRecord? = null;

    private const val SAMPLE_RATE = 16000 // 采样率 16kHz
    private const val FRAME_DURATION_MS = 20 // 每帧音频时长（毫秒）
    private const val BYTES_PER_SAMPLE = 2 // 每个采样点字节数（16bit=2字节）
    private const val MONO_CHANNEL = 1 // 单声道配置

    // 计算每帧字节数 = 时长 * 字节/样本 * 声道数 * (采样率/1000)  计算结果：640
    private const val WAVE_FRAME_SIZE =
        FRAME_DURATION_MS * BYTES_PER_SAMPLE * MONO_CHANNEL * SAMPLE_RATE / 1000

    private val inited = AtomicBoolean(false)


    // 识别结果处理器
    private var asrAliProcessor: AsrAliProcessor? = null


    private val nuiCallback: INativeNuiCallback = object : INativeNuiCallback {
        /**
         * SDK主要事件回调
         * @param event：回调事件，参见如下事件列表。
         * @param resultCode：参见错误码，在出现EVENT_ASR_ERROR事件时有效。
         * @param arg2：保留参数。
         * @param kwsResult：语音唤醒功能（暂不支持）。
         * @param asrResult：语音识别结果。
         */
        override fun onNuiEventCallback(
            event: Constants.NuiEvent?,
            resultCode: Int,
            arg2: Int,
            kwsResult: KwsResult?,
            asrResult: AsrResult?
        ) {
            asrAliProcessor?.processEvent(event, resultCode, asrResult)
        }


        /**
         * 开始识别时，此回调被连续调用，App需要在回调中进行语音数据填充。
         * @param buffer：填充语音的存储区。
         * @param len：需要填充语音的字节数。
         * @return：实际填充的字节数。
         */
        override fun onNuiNeedAudioData(buffer: ByteArray?, len: Int): Int {
            if (audioRecorder == null) {
                Logger.it(TAG, "audioRecorder is null")
                return -1
            }

            if (audioRecorder!!.state != AudioRecord.STATE_INITIALIZED) {
                Logger.it(TAG, "audioRecorder state:${audioRecorder!!.state}")
                return -1
            }

            var readSize = -1

            buffer?.run {
                readSize = audioRecorder!!.read(this, 0, len)
                asrAliProcessor?.processPcm(buffer)
            }



            return readSize
        }

        /**
         * 当start/stop/cancel等接口调用时，SDK通过此回调通知App进行录音的开关操作。
         * @param audioState：录音需要的状态（打开/关闭）
         */
        override fun onNuiAudioStateChanged(audioState: Constants.AudioState?) {
            Logger.it(TAG, "onNuiAudioStateChanged $audioState")
            audioState?.let {
                when (it) {
                    com.alibaba.idst.nui.Constants.AudioState.STATE_OPEN -> {
                        //开始录音
                        audioRecorder?.startRecording()
                    }

                    com.alibaba.idst.nui.Constants.AudioState.STATE_PAUSE -> {
                        //暂停录音
                        audioRecorder?.stop()
                    }

                    com.alibaba.idst.nui.Constants.AudioState.STATE_CLOSE -> {
                        //关闭录音
                        audioRecorder?.release()
                    }
                }
            }
        }


        /**
         * 音频能量值回调
         * @param volume: 音频数据能量值回调，范围-160至0，一般用于UI展示语音动效
         */
        override fun onNuiAudioRMSChanged(volume: Float) {
//            Logger.it(TAG, "onNuiAudioRMSChanged $volume")
        }

        /**
         * NUI VPR事件回调
         * @param event：VPR事件类型
         */
        override fun onNuiVprEventCallback(event: Constants.NuiVprEvent?) {

        }

    }


    fun init(appkey: String, token: String, deviceid: String) {
        if (inited.get()) {
            return
        }

        Logger.it(TAG, "appkey:$appkey,token:$token,deviceid:$deviceid")
        val workspace = CommonUtils.getModelPath(AppContext)


        val debugPath =
            "${AppStorage.External.Private.cachePath}debug_${System.currentTimeMillis()}"
        Logger.it(TAG, "workspace:$workspace")
        Logger.it(TAG, "debugPath:$debugPath")

        if (!File(debugPath).exists()) {
            File(debugPath).mkdirs()
        }

        CommonUtils.copyAssetsData(AppContext)

        val initParams = JSONObject().apply {
            put("workspace", workspace)//必填，且必须要有读写权限
            put("debug_path", debugPath)//可选，用于保存调试日志
            put("service_mode", Constants.ModeFullCloud)//必填
            put("app_key", appkey)
            put("token", token)
            put("deviceid", deviceid)
            put("url", "wss://nls-gateway.cn-shanghai.aliyuncs.com:443/ws/v1")//必填
        }.toString()

        Logger.it(TAG, "initParams:$initParams")

        val resultCode = nuiInstance.initialize(
            nuiCallback, initParams, Constants.LogLevel.LOG_LEVEL_VERBOSE, false
        )

        if (resultCode == Constants.NuiResultCode.SUCCESS) {
            Logger.it(TAG, "初始化成功")
            inited.set(true)
        } else {
            Logger.et(TAG, "初始化失败resultCode:${resultCode}")
        }
    }


    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    fun start(asrAliCallback: AsrAliCallback) {
        if (!inited.get()) {
            return
        }
        kotlin.runCatching {

            reset()

            this.asrAliProcessor = AsrAliProcessor(asrAliCallback).apply {
                start()
            }

            //先创建 audioRecorder
            audioRecorder = AudioRecord(
                MediaRecorder.AudioSource.DEFAULT, SAMPLE_RATE, //采样率
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, WAVE_FRAME_SIZE * 4
            )

            nuiInstance.setParams(genParams())

            //TYPE_P2T  push to talk:用户按下按钮说话，松开停止的场景
            val resultCode = nuiInstance.startDialog(Constants.VadMode.TYPE_P2T, "{}")

            if (resultCode == Constants.NuiResultCode.SUCCESS) {
                // 录音成功
                Logger.it(TAG, "录音成功")
            } else {
                asrAliCallback.onError("录音失败,resultCode:${resultCode}")
            }

        }.onFailure {
            asrAliCallback.onError(it.message ?: "未知错误")
        }

    }

    private fun reset() {
        audioRecorder?.release()
        audioRecorder = null
        asrAliProcessor?.release()
        asrAliProcessor = null
    }


    private fun genParams(): String {
        var params = ""

        val nlsConfig = JSONObject().apply {
            put("enable_intermediate_result", true)//是否返回中间结果
            put("enable_punctuation_prediction", true)//是否返回标点符号
            put("sample_rate", SAMPLE_RATE)//采样率
            put("sr_format", "pcm")//音频格式
        }

        val paramsJsonObject = JSONObject().apply {
            put("nls_config", nlsConfig)
            put("service_type", Constants.kServiceTypeSpeechTranscriber)// 语音听写
        }

        params = paramsJsonObject.toString()

        return params

    }


    fun stop() {
        if (!inited.get()) {
            return
        }
        nuiInstance.stopDialog()

    }

    fun release() {
        stop()
        nuiInstance.release()
    }


}