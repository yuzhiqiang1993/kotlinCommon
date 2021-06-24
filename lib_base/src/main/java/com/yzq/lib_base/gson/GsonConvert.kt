package com.yzq.lib_base.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * @description:json转换类
 * @author : yzq
 * @date   : 2018/7/10
 * @time   : 13:17
 *
 */


object GsonConvert {


    val gson: Gson = GsonBuilder()
        .serializeNulls()
        .setLenient()
        .create()

    val prettyGson: Gson = GsonBuilder()
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
        return gson.toJson(src)
    }

    fun toJson(src: Any, typeOfSrc: Type): String {
        return gson.toJson(src, typeOfSrc)
    }


}



