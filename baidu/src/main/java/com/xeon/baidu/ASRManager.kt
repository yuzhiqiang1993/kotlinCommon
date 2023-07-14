package com.xeon.asr_demo

import android.app.Application
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.yzq.logger.Logger

object ASRManager {

    const val TAG = "ASRManager"
    private lateinit var app: Application

    val instance by lazy {
        Logger.i(TAG, "创建ARS实例")
        EventManagerFactory.create(app, "asr")
    }

    fun init(app: Application) {
        this.app = app
    }

    fun getParam(): MutableMap<String, Any> {
        val params = LinkedHashMap<String, Any>()
        params.put(SpeechConstant.APP_KEY, "GRhUyHMWTN0uroHw6C2UyGas")
        params.put(SpeechConstant.APP_ID, "34600511")
        params.put(SpeechConstant.SECRET, "XhGaPuCrPLKkSVER12OGGLeuh92SpEMF")
        return params

    }


}