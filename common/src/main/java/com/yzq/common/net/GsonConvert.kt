package com.yzq.common.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.JsonReader
import java.io.Reader
import java.lang.reflect.Type


/**
 * @description:json转换类
 * @author : yzq
 * @date   : 2018/7/10
 * @time   : 13:17
 *
 */

object GsonConvert {


    private var gson: Gson

    init {
        gson = GsonBuilder()
                .serializeNulls()
                .create()
    }


    @Throws(JsonIOException::class, JsonSyntaxException::class)
    fun <T> fromJson(json: String, type: Class<T>): T {
        return gson.fromJson(json, type)
    }

    fun <T> fromJson(json: String, type: Type): T {
        return gson.fromJson(json, type)
    }

    @Throws(JsonIOException::class, JsonSyntaxException::class)
    fun <T> fromJson(reader: JsonReader, typeOfT: Type): T {
        return gson.fromJson(reader, typeOfT)
    }

    @Throws(JsonSyntaxException::class, JsonIOException::class)
    fun <T> fromJson(json: Reader, classOfT: Class<T>): T {
        return gson.fromJson(json, classOfT)
    }

    @Throws(JsonIOException::class, JsonSyntaxException::class)
    fun <T> fromJson(json: Reader, typeOfT: Type): T {
        return gson.fromJson(json, typeOfT)
    }

    fun toJson(src: Any): String {
        return gson.toJson(src)
    }

    fun toJson(src: Any, typeOfSrc: Type): String {
        return gson.toJson(src, typeOfSrc)
    }


}