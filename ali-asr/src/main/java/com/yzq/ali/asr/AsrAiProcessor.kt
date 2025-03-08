package com.yzq.ali.asr

import android.os.Handler
import android.os.Looper
import com.alibaba.idst.nui.AsrResult
import com.alibaba.idst.nui.Constants
import com.yzq.ali.asr.data.SentenceResult
import com.yzq.logger.Logger
import org.json.JSONObject
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 阿里云语音识别处理器
 * 负责处理语音识别的结果，包括中间结果和最终结果的处理
 *
 * @property asrAliCallback 语音识别回调接口
 * @author yuzhiqiang
 */
class AsrAliProcessor(private val asrAliCallback: AsrAliCallback) {

    companion object {
        private const val TAG = "AsrAliProcessor"
    }

    // 识别器状态
    private val isProcessing = AtomicBoolean(false)

    // 主线程 Handler
    private val mainHandler = Handler(Looper.getMainLooper())

    // 存储中间结果的 map
    private val partialResultMap = ConcurrentHashMap<Int, SentenceResult>()

    // 最终确定结果的集合
    private val confirmedSentences = CopyOnWriteArrayList<String>()

    /**
     * 处理语音识别事件
     * @param event 事件类型
     * @param resultCode 结果码
     * @param asrResult 识别结果
     */
    fun processEvent(event: Constants.NuiEvent?, resultCode: Int, asrResult: AsrResult?) {
        when (event) {
            // 处理部分识别结果
            Constants.NuiEvent.EVENT_ASR_PARTIAL_RESULT -> handlePartialResult(asrResult)
            // 处理句子结束
            Constants.NuiEvent.EVENT_SENTENCE_END -> handleSentenceEnd(asrResult)
            // 处理转录完成
            Constants.NuiEvent.EVENT_TRANSCRIBER_COMPLETE -> handleTranscriberComplete()
            // 处理麦克风错误
            Constants.NuiEvent.EVENT_MIC_ERROR -> handleError("麦克风错误", resultCode)
            // 处理识别错误
            Constants.NuiEvent.EVENT_ASR_ERROR -> handleError("识别错误", resultCode)
            // 处理其他未处理的事件类型
            else -> Logger.it(TAG, "未处理的事件类型：$event")
        }
    }

    /**
     * 处理中间识别结果
     */
    private fun handlePartialResult(asrResult: AsrResult?) {
        parseAsrResponse(asrResult)?.let { (index, result) ->
            partialResultMap.getOrPut(index) {
                SentenceResult(index, "", result)
            }.apply {
                draftText = result
            }
            notifyResultChanged(buildCurrentText(), false)
        }
    }

    /**
     * 处理句子结束事件
     */
    private fun handleSentenceEnd(asrResult: AsrResult?) {
        parseAsrResponse(asrResult)?.let { (index, result) ->
            partialResultMap[index]?.let { sentence ->
                sentence.finalText = result
                confirmedSentences.add(result)
                partialResultMap.remove(index)
            } ?: confirmedSentences.add(result)

            notifyResultChanged(buildCurrentText(), false)
        }
    }

    /**
     * 处理识别完成事件
     */
    private fun handleTranscriberComplete() {
        isProcessing.set(false)
        notifyResultChanged(buildCurrentText(), true)
    }

    /**
     * 处理错误事件
     */
    private fun handleError(errorType: String, code: Int) {
        isProcessing.set(false)
        val errorMessage = "$errorType:$code"
        Logger.et(TAG, errorMessage)
        mainHandler.post { asrAliCallback.onError(errorMessage) }
    }

    /**
     * 解析阿里云返回的JSON数据
     * @return Pair<索引, 结果文本>
     */
    private fun parseAsrResponse(asrResult: AsrResult?): Pair<Int, String>? {
        return try {
            val json = JSONObject(asrResult?.asrResult.orEmpty())
            val payload = json.getJSONObject("payload")
            val index = payload.getInt("index")
            val result = payload.getString("result")
            index to result
        } catch (e: Exception) {
            Logger.et(TAG, "JSON解析失败", e)
            null
        }
    }

    /**
     * 构建当前文本
     * 将已确认的结果和中间结果拼接
     */
    private fun buildCurrentText(): String {
        return buildString {
            append(confirmedSentences.joinToString(""))
            append(partialResultMap.values.sortedBy { it.index }.joinToString("") { it.draftText })
        }
    }

    /**
     * 通知结果变化
     */
    private fun notifyResultChanged(text: String, isFinished: Boolean) {
        Logger.it(TAG, "识别结果: $text, 是否完成: $isFinished")
        mainHandler.post { asrAliCallback.onResult(text, isFinished) }
    }

    /**
     * 获取最终识别结果列表
     */
    fun getFinalResult(): List<String> = confirmedSentences.toList()

    /**
     * 处理 PCM 音频数据
     * @param buffer PCM音频数据
     */
    fun processPcm(buffer: ByteArray) {
        if (!isProcessing.get()) {
            return
        }
        
        kotlin.runCatching {
            // TODO: 实现 PCM 数据处理逻辑
        }.onFailure { e ->
            Logger.et(TAG, "PCM处理失败", e)
            handleError("PCM处理错误", -1)
        }
    }

    /**
     * 开始处理
     */
    fun start() {
        isProcessing.set(true)
        partialResultMap.clear()
        confirmedSentences.clear()
    }

    /**
     * 停止处理
     */
    fun stop() {
        isProcessing.set(false)
        mainHandler.removeCallbacksAndMessages(null)
    }

    /**
     * 释放资源
     */
    fun release() {
        stop()
        partialResultMap.clear()
        confirmedSentences.clear()
    }
}
