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
 * 阿里云语音识别管理类
 * 负责语音识别的初始化、启动、停止等生命周期管理
 * @author yuzhiqiang
 */
object AsrAliManager {
    private const val TAG = "AliAsrManager"

    /**
     * 音频配置
     */
    private object AudioConfig {
        const val SAMPLE_RATE = 16000 // 采样率 16kHz
        const val FRAME_DURATION_MS = 20 // 每帧音频时长（毫秒）
        const val BYTES_PER_SAMPLE = 2 // 每个采样点字节数（16bit=2字节）
        const val MONO_CHANNEL = 1 // 单声道配置

        // 计算每帧字节数 = 时长 * 字节/样本 * 声道数 * (采样率/1000)
        const val WAVE_FRAME_SIZE =
            FRAME_DURATION_MS * BYTES_PER_SAMPLE * MONO_CHANNEL * SAMPLE_RATE / 1000
    }

    /**
     * 识别状态
     */
    private enum class RecognitionState {
        UNINITIALIZED,    // 未初始化
        INITIALIZED,      // 已初始化
        RECOGNIZING,      // 识别中
        ERROR            // 错误状态
    }

    private val nuiInstance by lazy { NativeNui() }
    private var audioRecorder: AudioRecord? = null
    private var asrAliProcessor: AsrAliProcessor? = null

    private val inited = AtomicBoolean(false)
    private var currentState = RecognitionState.UNINITIALIZED
        private set(value) {
            field = value
            Logger.it(TAG, "状态变更: $field")
        }

    /**
     * NUI 回调实现
     */
    private val nuiCallback = object : INativeNuiCallback {
        override fun onNuiEventCallback(
            event: Constants.NuiEvent?,
            resultCode: Int,
            arg2: Int,
            kwsResult: KwsResult?,
            asrResult: AsrResult?
        ) {
            asrAliProcessor?.processEvent(event, resultCode, asrResult)
        }

        override fun onNuiNeedAudioData(buffer: ByteArray?, len: Int): Int {
            return handleAudioData(buffer, len)
        }

        override fun onNuiAudioStateChanged(audioState: Constants.AudioState?) {
            handleAudioStateChange(audioState)
        }

        override fun onNuiAudioRMSChanged(volume: Float) {
            // 可选：处理音量变化
        }

        override fun onNuiVprEventCallback(event: Constants.NuiVprEvent?) {
            // VPR 事件处理（如需要）
        }
    }

    /**
     * 初始化识别器
     */
    fun init(appkey: String, token: String, deviceId: String) {
        if (inited.get()) {
            Logger.it(TAG, "已经初始化过")
            return
        }

        try {
            val initParams = createInitParams(appkey, token, deviceId)
            val resultCode = initializeNui(initParams)
            handleInitResult(resultCode)
        } catch (e: Exception) {
            Logger.et(TAG, "初始化失败", e)
            currentState = RecognitionState.ERROR
        }
    }

    /**
     * 创建初始化参数
     */
    private fun createInitParams(appkey: String, token: String, deviceId: String): String {
        val workspace = CommonUtils.getModelPath(AppContext)
        val debugPath =
            "${AppStorage.External.Private.cachePath}debug_${System.currentTimeMillis()}"

        File(debugPath).mkdirs()
        CommonUtils.copyAssetsData(AppContext)

        return JSONObject().apply {
            put("workspace", workspace)
            put("debug_path", debugPath)
            put("service_mode", Constants.ModeFullCloud)
            put("app_key", appkey)
            put("token", token)
            put("deviceid", deviceId)
            put("url", "wss://nls-gateway.cn-shanghai.aliyuncs.com:443/ws/v1")
        }.toString()
    }

    /**
     * 初始化 NUI
     */
    private fun initializeNui(params: String): Int {
        return nuiInstance.initialize(
            nuiCallback,
            params,
            Constants.LogLevel.LOG_LEVEL_VERBOSE,
            false
        )
    }

    /**
     * 处理初始化结果
     */
    private fun handleInitResult(resultCode: Int) {
        if (resultCode == Constants.NuiResultCode.SUCCESS) {
            Logger.it(TAG, "初始化成功")
            inited.set(true)
            currentState = RecognitionState.INITIALIZED
        } else {
            Logger.et(TAG, "初始化失败 resultCode: $resultCode")
            currentState = RecognitionState.ERROR
            throw IllegalStateException("初始化失败: $resultCode")
        }
    }

    /**
     * 开始识别
     */
    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    fun start(asrAliCallback: AsrAliCallback) {
        if (!inited.get()) {
            Logger.et(TAG, "未初始化")
            return
        }

        kotlin.runCatching {
            prepareRecognition(asrAliCallback)
            startRecognition()
        }.onFailure { e ->
            handleStartError(e, asrAliCallback)
        }
    }

    /**
     * 准备识别
     */
    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    private fun prepareRecognition(asrAliCallback: AsrAliCallback) {
        reset()

        asrAliProcessor = AsrAliProcessor(asrAliCallback).apply {
            start()
        }

        audioRecorder = createAudioRecorder()
        nuiInstance.setParams(createRecognitionParams())
    }

    /**
     * 创建音频记录器
     */
    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    private fun createAudioRecorder(): AudioRecord {
        return AudioRecord(
            MediaRecorder.AudioSource.DEFAULT,
            AudioConfig.SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            AudioConfig.WAVE_FRAME_SIZE * 4
        )
    }

    /**
     * 创建识别参数
     */
    private fun createRecognitionParams(): String {
        return JSONObject().apply {
            put("nls_config", JSONObject().apply {
                put("enable_intermediate_result", true)
                put("enable_punctuation_prediction", true)
                put("sample_rate", AudioConfig.SAMPLE_RATE)
                put("sr_format", "pcm")
            })
            put("service_type", Constants.kServiceTypeSpeechTranscriber)
        }.toString()
    }

    /**
     * 开始识别
     */
    private fun startRecognition() {
        val resultCode = nuiInstance.startDialog(Constants.VadMode.TYPE_P2T, "{}")
        if (resultCode == Constants.NuiResultCode.SUCCESS) {
            Logger.it(TAG, "开始识别成功")
            currentState = RecognitionState.RECOGNIZING
        } else {
            throw IllegalStateException("开始识别失败: $resultCode")
        }
    }

    /**
     * 处理开始识别错误
     */
    private fun handleStartError(e: Throwable, asrAliCallback: AsrAliCallback) {
        val errorMessage = e.message ?: "未知错误"
        Logger.et(TAG, "开始识别失败", e)
        currentState = RecognitionState.ERROR
        asrAliCallback.onError(errorMessage)
    }

    /**
     * 处理音频数据
     */
    private fun handleAudioData(buffer: ByteArray?, len: Int): Int {
        val recorder = audioRecorder ?: return -1

        if (recorder.state != AudioRecord.STATE_INITIALIZED) {
            Logger.it(TAG, "audioRecorder state: ${recorder.state}")
            return -1
        }

        return buffer?.let {
            recorder.read(it, 0, len).also { readSize ->
                if (readSize > 0) {
                    asrAliProcessor?.processPcm(buffer)
                }
            }
        } ?: -1
    }

    /**
     * 处理音频状态变化
     */
    private fun handleAudioStateChange(audioState: Constants.AudioState?) {
        Logger.it(TAG, "音频状态变化: $audioState")

        when (audioState) {
            Constants.AudioState.STATE_OPEN -> audioRecorder?.startRecording()
            Constants.AudioState.STATE_PAUSE -> audioRecorder?.stop()
            Constants.AudioState.STATE_CLOSE -> audioRecorder?.release()
            else -> Logger.it(TAG, "未处理的音频状态: $audioState")
        }
    }

    /**
     * 重置状态
     */
    private fun reset() {
        audioRecorder?.release()
        audioRecorder = null
        asrAliProcessor?.release()
        asrAliProcessor = null
    }

    /**
     * 停止识别
     */
    fun stop() {
        if (!inited.get()) return

        nuiInstance.stopDialog()
        currentState = RecognitionState.INITIALIZED
    }

    /**
     * 释放资源
     */
    fun release() {
        stop()
        reset()
        nuiInstance.release()
        inited.set(false)
        currentState = RecognitionState.UNINITIALIZED
    }
}