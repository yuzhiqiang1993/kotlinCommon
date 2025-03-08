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


/**
 * @description: 用于处理识别的结果
 * @author : yuzhiqiang
 */

class AsrAliProcessor(private val asrAliCallback: AsrAliCallback) {

    companion object {
        const val TAG = "AsrAliProcessor"
    }

    // 存储中间结果的 map
    private val partalResultMap = ConcurrentHashMap<Int, SentenceResult>()

    // 最终确定结果的集合，存储一句话结果
    private val confirmedSentences = CopyOnWriteArrayList<String>()

    // 处理事件回调
    fun processEvent(event: Constants.NuiEvent?, resultCode: Int, asrResult: AsrResult?) {
        when (event) {
            Constants.NuiEvent.EVENT_ASR_PARTIAL_RESULT -> {
                //中间结果
                parseAsrResponse(asrResult)?.let { (index, result) ->
                    partalResultMap.getOrPut(index) {
                        //对应的 index 不存在时，创建
                        SentenceResult(index, "", result)
                    }.apply {
                        //更新中间结果文本
                        draftText = result
                    }

                    callbakResult(buildCurrentText(), false)

                }
            }

            Constants.NuiEvent.EVENT_SENTENCE_END -> {
                // 一句话结束
                parseAsrResponse(asrResult)?.let { (index, result) ->
                    partalResultMap[index]?.let { sentence ->
                        // 标记最终结果
                        sentence.finalText = result
                        confirmedSentences.add(result)
                        //移除对应的中间结果
                        partalResultMap.remove(index)
                    } ?: run {
                        // 直接添加未缓存的最终结果
                        confirmedSentences.add(result)
                    }

                    callbakResult(buildCurrentText(), false)
                }
            }

            Constants.NuiEvent.EVENT_TRANSCRIBER_COMPLETE -> {
                // 识别结束
                callbakResult(buildCurrentText(), true)
            }

            Constants.NuiEvent.EVENT_MIC_ERROR -> {
                // 麦克风错误
                asrAliCallback.onError("麦克风错误:$resultCode")
            }

            Constants.NuiEvent.EVENT_ASR_ERROR -> {
                // 识别错误
                asrAliCallback.onError("识别错误:$resultCode")
            }

            else -> {
            }
        }
    }

    // 解析阿里云返回的JSON数据
    private fun parseAsrResponse(asrResult: AsrResult?): Pair<Int, String>? {
        return try {
            val json = JSONObject(asrResult?.asrResult.orEmpty())
            val payload = json.getJSONObject("payload")
            val index = payload.getInt("index")
            val result = payload.getString("result")
            index to result
        } catch (e: Exception) {
            Logger.et("JSON解析失败", e)
            null
        }
    }


    /**
     * 本质上就是已确定的结果拼接上中间文本
     * @return String
     */
    private fun buildCurrentText(): String {
        return confirmedSentences.joinToString("") + partalResultMap.values.sortedBy { it.index }
            .joinToString("") { it.draftText }
    }

    private fun callbakResult(text: String, isFinished: Boolean) {
        Logger.it(TAG, "callbakResult text:$text,isFinished:$isFinished")
        Handler(Looper.getMainLooper()).post {
            asrAliCallback.onResult(text, isFinished)
        }
    }

    fun getFinalResult() = confirmedSentences.toList()

    /**
     * 处理 pcm 数据
     * @param buffer ByteArray?
     */
    fun processPcm(buffer: ByteArray) {
        kotlin.runCatching {

        }


    }
}
