package com.xeon.baidu

import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.yzq.application.AppManager
import com.yzq.logger.Logger
import org.json.JSONObject
import java.util.concurrent.atomic.AtomicBoolean


/**
 * @description:百度 ASR 管理类
 * @author : yuzhiqiang
 */

object ASRBaiduManager {

    const val TAG = "ASRManager"

    private var appId: String = ""
    private var ak: String = ""
    private var sk: String = ""

    private val initialized = AtomicBoolean(false)

    private val eventListenerList: MutableList<EventListener> = mutableListOf()

    private var eventManager: EventManager? = null


    fun init(appId: String, ak: String, sk: String) {

        if (initialized.compareAndSet(false, true)) {
            Logger.it(TAG, "初始化ASR")
            this.appId = appId
            this.ak = ak
            this.sk = sk
//            AutoCheck(
//                AppManager.application, object : Handler(Looper.getMainLooper()) {
//                    override fun handleMessage(msg: android.os.Message) {
//                        if (msg.what == 100) {
//                            val autoCheck = msg.obj as AutoCheck
//                            synchronized(autoCheck) {
//                                val message = autoCheck.obtainErrorMessage()
//                                Logger.it(TAG, "检查结果: ${message}")
//                            }
//                        }
//                    }
//                }, false
//            ).checkAsr(getParam())

        } else {
            Logger.it(TAG, "ASR已经初始化")
        }


    }

    private fun getParam(): MutableMap<String, Any> {
        val params = LinkedHashMap<String, Any>()
        params.put(SpeechConstant.APP_KEY, ak)
        params.put(SpeechConstant.APP_ID, appId)
        params.put(SpeechConstant.SECRET, sk)

        params.put(
            SpeechConstant.ACCEPT_AUDIO_DATA, true
        )//是否需要语音音频数据回调，开启后有 CALLBACK_EVENT_ASR_AUDIO 事件
        params.put(
            SpeechConstant.ACCEPT_AUDIO_VOLUME, true
        )//是否需要语音音量数据回调，开启后有 CALLBACK_EVENT_ASR_VOLUME 事件

        params.put(
            SpeechConstant.VAD, SpeechConstant.VAD_TOUCH
        )//关闭语音活动检测，适合用户自行控制音频结束，如按住说话松手停止的场景。功能等同于60s限制的长语音。需要手动调用 ASR_STOP 停止录音
        return params

    }


    fun startRecognition(listener: EventListener) {
        if (initialized.get()) {
            Logger.it(TAG, "开始识别")
            createEventManager()
            registerListener(listener)
            val jsonParam =
                kotlin.runCatching { JSONObject(getParam().toMap()).toString() }.getOrDefault("{}")
            Logger.it(TAG, "jsonParam:${jsonParam}")
            eventManager?.send(SpeechConstant.ASR_START, jsonParam, null, 0, 0)
        }

    }

    fun stopRecognition() {
        //停止识别
        eventManager?.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0)
    }

    fun cancelRecognition() {
        eventManager?.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0)
    }

    fun destroyRecognition() {
        eventManager?.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0)
        eventListenerList.forEach {
            eventManager?.unregisterListener(it)
        }
        eventListenerList.clear()
        eventManager = null
    }

    @Synchronized
    private fun createEventManager() {
        if (eventManager == null) {
            this.eventManager = EventManagerFactory.create(AppManager.application, "asr")
        } else {
            Logger.it(TAG, "已经创建过EventManager")
        }

    }


    private fun registerListener(listener: EventListener) {
        if (!eventListenerList.contains(listener)) {
            eventListenerList.add(listener)
            eventManager?.registerListener(listener)
        }
    }

}