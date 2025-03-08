package com.yzq.ifly.asr

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
 * 讯飞语音识别管理类
 *
 * 该类负责管理讯飞语音识别的完整生命周期，包括：
 * 1. 初始化讯飞 SDK
 * 2. 创建和配置语音识别器
 * 3. 管理识别过程
 * 4. 处理识别结果
 * 5. 资源释放
 *
 * 使用单例模式确保全局唯一实例
 *
 * @author yuzhiqiang
 */
object AsrIflyManager {
    private const val TAG = "AsrIflyManager"

    /**
     * 识别配置参数
     *
     * 包含讯飞语音识别所需的各项配置参数：
     * - LANGUAGE: 识别语言，默认中文
     * - VAD_BOS: 前端点超时时间，即用户多长时间不说话则当做超时处理
     * - VAD_EOS: 后端点超时时间，即用户停止说话多长时间内即认为不再输入
     * - ASR_PTT: 标点符号开关，1-开启，0-关闭
     * - AUDIO_FORMAT: 音频格式，支持 pcm、wav 等
     * - RESULT_TYPE: 返回结果格式，使用 json
     * - DYNAMIC_CORRECTION: 动态修正开关，wpgs-开启动态修正
     */
    private object RecognitionConfig {
        const val LANGUAGE = "zh_cn"           // 识别语言
        const val VAD_BOS = "4000"             // 前端点超时时间（毫秒）
        const val VAD_EOS = "5000"             // 后端点超时时间（毫秒）
        const val ASR_PTT = "1"                // 是否显示标点符号
        const val AUDIO_FORMAT = "pcm"         // 音频格式
        const val RESULT_TYPE = "json"         // 返回结果格式
        const val DYNAMIC_CORRECTION = "wpgs"   // 动态修正开关
    }

    // 初始化状态标志，使用 AtomicBoolean 确保线程安全
    private val initialized = AtomicBoolean(false)

    // 语音识别器实例
    private var speechRecognizer: SpeechRecognizer? = null

    /**
     * 存储识别结果的 Map
     * key: sn (sentence number) - 每句话的唯一标识
     * value: 该句话的识别文本
     *
     * 使用 mutableMapOf 而不是 ConcurrentHashMap 的原因是：
     * 1. 讯飞 SDK 的回调都是在主线程中
     * 2. 我们对 Map 的操作都在同一个线程中，不需要线程安全
     */
    private val recognitionResults = mutableMapOf<String, String>()

    /**
     * 初始化监听器
     *
     * 用于监听讯飞 SDK 的初始化状态：
     * - 成功：ErrorCode.SUCCESS
     * - 失败：其他错误码，可通过讯飞官网错误码文档查询具体原因
     */
    private val initListener = InitListener { code ->
        if (code != ErrorCode.SUCCESS) {
            Logger.et(
                TAG,
                "初始化失败,错误码：$code,请点击网址https://www.xfyun.cn/document/error-code查询解决方案"
            )
        } else {
            Logger.it(TAG, "初始化成功")
        }
    }

    /**
     * 初始化语音识别
     *
     * 使用 compareAndSet 确保只初始化一次
     * 初始化成功后，才能进行后续的语音识别操作
     *
     * @param appId 讯飞开放平台申请的 APPID
     */
    fun init(appId: String) {
        if (initialized.compareAndSet(false, true)) {
            SpeechUtility.createUtility(
                AppManager.application,
                "${SpeechConstant.APPID}=$appId"
            )
        }
    }

    /**
     * 开始语音识别
     *
     * 1. 检查初始化状态
     * 2. 确保识别器已创建
     * 3. 清除上次的识别结果
     * 4. 开始新的识别
     *
     * @param listener 识别过程的监听器，包含识别结果、音量变化、开始识别、结束识别等回调
     */
    fun startRecognition(listener: RecognizerListener) {
        if (!initialized.get()) {
            Logger.et(TAG, "未初始化")
            return
        }

        Logger.it(TAG, "开始识别")
        initializeRecognizerIfNeeded()
        clearRecognitionResults()
        speechRecognizer?.startListening(listener)
    }

    /**
     * 初始化识别器（如果需要）
     *
     * 采用懒加载方式，仅在首次使用时创建识别器
     */
    private fun initializeRecognizerIfNeeded() {
        if (speechRecognizer == null) {
            createSpeechRecognizer()
        }
    }

    /**
     * 创建并配置语音识别器
     *
     * 1. 创建识别器实例
     * 2. 配置识别参数
     */
    private fun createSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createRecognizer(
            AppManager.application,
            initListener
        )?.apply {
            configureRecognizer(this)
        }
    }

    /**
     * 配置识别器参数
     *
     * 设置识别所需的各项参数，包括：
     * - 语言
     * - 超时时间
     * - 标点符号
     * - 音频格式
     * - 动态修正等
     *
     * @param recognizer 要配置的识别器实例
     */
    private fun configureRecognizer(recognizer: SpeechRecognizer) {
        with(recognizer) {
            setParameter(SpeechConstant.PARAMS, null)
            setParameter("dwa", RecognitionConfig.DYNAMIC_CORRECTION)
            setParameter(SpeechConstant.RESULT_TYPE, RecognitionConfig.RESULT_TYPE)
            setParameter(SpeechConstant.LANGUAGE, RecognitionConfig.LANGUAGE)
            setParameter(SpeechConstant.VAD_BOS, RecognitionConfig.VAD_BOS)
            setParameter(SpeechConstant.VAD_EOS, RecognitionConfig.VAD_EOS)
            setParameter(SpeechConstant.ASR_PTT, RecognitionConfig.ASR_PTT)
            setParameter(SpeechConstant.AUDIO_FORMAT, RecognitionConfig.AUDIO_FORMAT)
        }
    }

    /**
     * 处理语音识别结果
     *
     * 将识别结果解析为文本，处理可能出现的异常
     *
     * @param result 讯飞返回的识别结果
     * @return 当前完整的识别文本
     */
    fun handleSpeechResult(result: RecognizerResult): String = kotlin.runCatching {
        val jsonResult = JSONObject(result.resultString)
        processRecognitionResult(jsonResult)
    }.getOrElse {
        Logger.et(TAG, "处理识别结果失败", it)
        getCurrentRecognitionText()
    }

    /**
     * 处理识别结果 JSON
     *
     * 解析 JSON 格式的识别结果，处理：
     * 1. 普通识别文本
     * 2. 动态修正内容
     *
     * @param jsonResult JSON 格式的识别结果
     * @return 当前完整的识别文本
     */
    private fun processRecognitionResult(jsonResult: JSONObject): String {
        val pgs = jsonResult.optString("pgs")
        val sn = jsonResult.optString("sn")
        val recognizedText = parseRecognizedText(jsonResult)

        // 处理动态修正
        if (pgs == "rpl") {
            handleDynamicCorrection(jsonResult)
        }

        // 更新当前识别结果
        recognitionResults[sn] = recognizedText

        return getCurrentRecognitionText()
    }

    /**
     * 解析识别文本
     *
     * 从 JSON 结果中提取识别的文本内容
     * JSON 结构：ws -> cw -> w
     *
     * @param jsonResult JSON 格式的识别结果
     * @return 解析出的文本
     */
    private fun parseRecognizedText(jsonResult: JSONObject): String {
        val wsArray = jsonResult.optJSONArray("ws") ?: return ""
        return buildString {
            for (i in 0 until wsArray.length()) {
                val wordObj = wsArray.getJSONObject(i)
                val cwArray = wordObj.optJSONArray("cw")
                if ((cwArray?.length() ?: 0) > 0) {
                    append(cwArray?.getJSONObject(0)?.optString("w"))
                }
            }
        }
    }

    /**
     * 处理动态修正
     *
     * 当出现动态修正时，需要：
     * 1. 获取修正范围 [begin, end]
     * 2. 删除该范围内的旧识别结果
     *
     * @param jsonResult 包含修正信息的 JSON 结果
     */
    private fun handleDynamicCorrection(jsonResult: JSONObject) {
        val rg = jsonResult.optString("rg")
        val (begin, end) = rg.trim('[', ']').split(",").map { it.toInt() }
        for (i in begin..end) {
            recognitionResults.remove(i.toString())
        }
    }

    /**
     * 获取当前完整的识别文本
     *
     * 将所有已识别的文本按顺序拼接
     *
     * @return 完整的识别文本
     */
    private fun getCurrentRecognitionText(): String {
        return recognitionResults.values.joinToString("")
    }

    /**
     * 清除识别结果
     *
     * 在开始新的识别前，清除上次的识别结果
     */
    private fun clearRecognitionResults() {
        recognitionResults.clear()
    }

    /**
     * 停止识别
     *
     * 停止当前的语音识别，但不会清除识别结果
     */
    fun stopRecognition() {
        speechRecognizer?.stopListening()
    }

    /**
     * 取消识别
     *
     * 取消当前的语音识别，会清除识别结果
     */
    fun cancelRecognition() {
        speechRecognizer?.cancel()
    }

    /**
     * 销毁识别器
     *
     * 完整的资源释放流程：
     * 1. 取消当前识别
     * 2. 销毁识别器
     * 3. 清除识别结果
     *
     * 通常在不再需要语音识别功能时调用
     */
    fun destroyRecognition() {
        cancelRecognition()
        speechRecognizer?.destroy()
        speechRecognizer = null
        clearRecognitionResults()
    }
}
