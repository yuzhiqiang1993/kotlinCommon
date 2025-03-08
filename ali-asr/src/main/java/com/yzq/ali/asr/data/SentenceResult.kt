package com.yzq.ali.asr.data


/**
 * @description: 分句结果
 * @author : yuzhiqiang
 */

data class SentenceResult(
    val index: Int, // 句子序号
    var draftText: String,// 中间草稿
    var finalText: String?, // 最终结果（带标点）
)