package com.yzq.ali.asr

interface AsrAliCallback {

    /**
     * 识别结果
     */
    fun onResult(result: String, isFinish: Boolean)

    /**
     * 识别出错
     */
    fun onError(error: String)

    /**
     * pcm 数据以及音量
     */
    fun onPcmData(data: ByteArray, volume: Int)

}