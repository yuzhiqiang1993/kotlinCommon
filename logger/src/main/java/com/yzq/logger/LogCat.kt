package com.yzq.logger

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.lang.Thread.currentThread
import kotlin.math.min


/**
 * @description 日志打印
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

object LogCat {

    enum class Type {
        VERBOSE, DEBUG, INFO, WARN, ERROR, WTF
    }

    /* 当前平台的换行符 */
    private val LINE_SEP = System.getProperty("line.separator")

    /** 日志默认标签 */
    var tag = "LogCat"

    /** 是否启用日志 */
    var enabled = true

    /** 日志是否显示代码位置 */
    var traceEnabled = true

    /** 日志的Hook钩子 */
    val logHooks by lazy { ArrayList<LogHook>() }

    /**
     * @param enabled 是否启用日志
     * @param tag 日志默认标签
     */
    fun setDebug(enabled: Boolean, tag: String = this.tag) {
        this.enabled = enabled
        this.tag = tag
    }

    /**
     * 添加日志拦截器
     */
    fun addHook(hook: LogHook) {
        logHooks.add(hook)
    }

    /**
     * 删除日志拦截器
     */
    fun removeHook(hook: LogHook) {
        logHooks.remove(hook)
    }


    @JvmOverloads
    @JvmStatic
    fun v(
        msg: String,
        tag: String = this.tag,
        tr: Throwable? = null,
        occurred: Throwable? = Exception()
    ) {

        print(Type.VERBOSE, msg, tag, tr, occurred)
    }

    @JvmOverloads
    @JvmStatic
    fun i(
        msg: String,
        tag: String = this.tag,
        tr: Throwable? = null,
        occurred: Throwable? = Exception()
    ) {
        print(Type.INFO, msg, tag, tr, occurred)
    }

    @JvmOverloads
    @JvmStatic
    fun d(
        msg: String,
        tag: String = this.tag,
        tr: Throwable? = null,
        occurred: Throwable? = Exception()
    ) {
        print(Type.DEBUG, msg, tag, tr, occurred)
    }

    @JvmOverloads
    @JvmStatic
    fun w(
        msg: String,
        tag: String = this.tag,
        tr: Throwable? = null,
        occurred: Throwable? = Exception()
    ) {
        print(Type.WARN, msg, tag, tr, occurred)
    }

    @JvmOverloads
    @JvmStatic
    fun e(
        msg: String,
        tag: String = this.tag,
        tr: Throwable? = null,
        occurred: Throwable? = Exception()
    ) {
        print(Type.ERROR, msg, tag, tr, occurred)
    }


    @JvmOverloads
    @JvmStatic
    fun wtf(
        msg: String,
        tag: String = this.tag,
        tr: Throwable? = null,
        occurred: Throwable? = Exception()
    ) {
        print(Type.WTF, msg, tag, tr, occurred)
    }

    /**
     * 输出日志
     * 如果[msg]和[occurred]为空或者[tag]为空将不会输出日志, 拦截器
     *
     * @param type 日志等级
     * @param msg 日志信息
     * @param tag 日志标签
     * @param occurred 日志异常
     */
    private fun print(
        type: Type = Type.INFO,
        msg: String,
        tag: String = this.tag,
        tr: Throwable? = null,
        occurred: Throwable? = Exception()
    ) {
        if (!enabled) return


        var traceInfo = ""

        if (traceEnabled && occurred != null) {
            occurred.stackTrace.getOrNull(1)?.run {
                traceInfo = "${className}.$methodName($fileName:$lineNumber)"
            }
        }
        val info = LogInfo(type, msg, tag, tr, occurred)
        for (logHook in logHooks) {
            logHook.hook(info)
            if (info.msg == null) return
        }

        val sb = StringBuilder()
        sb.appendLine("┌───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────")
        sb.appendLine("│ $tag | ${currentThread().name} | ${traceInfo}")
        sb.appendLine("├───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────")
        msg.toString().split(LINE_SEP).forEach {
            sb.appendLine("│ $it")
        }
        sb.appendLine("└───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────")


        val message = sb.toString()
        val max = 3800
        val length = message.length
        if (length > max) {
            synchronized(this) {
                var startIndex = 0
                var endIndex = max
                while (startIndex < length) {
                    endIndex = min(length, endIndex)
                    val substring = message.substring(startIndex, endIndex)
                    log(type, substring, tag, tr)
                    startIndex += max
                    endIndex += max
                }
            }
        } else {
            log(type, message, tag, tr)
        }
    }

    /**
     * JSON格式化输出日志
     * @param tag 日志标签
     * @param msg 日志信息
     * @param type 日志类型
     * @param occurred 日志发生位置
     */
    @JvmOverloads
    @JvmStatic
    fun json(
        json: String,
        tag: String = this.tag,
        msg: String = "",
        type: Type = Type.INFO,
        occurred: Throwable? = Exception()
    ) {
        if (!enabled) return

        val tokener = JSONTokener(json)

        val obj = runCatching {
            tokener.nextValue()//解析字符串返回根对象
        }.getOrDefault(" Parse json error")

        val message = when (obj) {
            is JSONObject -> obj.toString(2) //转成格式化的json
            is JSONArray -> obj.toString(2) //转成格式化的json
            else -> obj.toString()
        }

        print(type, message, tag, occurred = occurred)
    }

    private fun log(type: Type, msg: String, tag: String, tr: Throwable?) {
        when (type) {
            Type.VERBOSE -> Log.v(tag, msg, tr)
            Type.DEBUG -> Log.d(tag, msg, tr)
            Type.INFO -> Log.i(tag, msg, tr)
            Type.WARN -> Log.w(tag, msg, tr)
            Type.ERROR -> Log.e(tag, msg, tr)
            Type.WTF -> Log.wtf(tag, msg, tr)
        }
    }
}