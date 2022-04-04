package com.yzq.lib_base.utils.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * @description: json解析
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/5/30
 * @time   : 10:30
 */

object GsonUtil {

    val gson: Gson = GsonBuilder()
        .serializeNulls()
        .setLenient()
        .create()

    private val prettyGson: Gson = GsonBuilder()
        .serializeNulls()
        .setPrettyPrinting()
        .setLenient()
        .create()


    fun <T> fromJson(json: String, classOfT: Class<T>): T {
        return gson.fromJson(json, classOfT)
    }

    inline fun <reified T> fromJson(json: String): T {
        return gson.fromJson(json, object : TypeToken<T>() {}.type)
    }

    fun toJson(src: Any): String {
        return gson.toJson(src)
    }

    fun toPrettyJson(src: Any): String {
        return prettyGson.toJson(src)
    }

    fun toJson(src: Any, typeOfSrc: Type): String {
        return gson.toJson(src, typeOfSrc)
    }


}