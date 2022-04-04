package com.yzq.lib_base.utils.json

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * @description: 基于moshi的json转换封装
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/3/13
 * @time   : 6:29 下午
 */

object MoshiUtils {

    val builder = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    fun toJson(src: Any): String {
        return builder.adapter(Any::class.java).toJson(src)
    }

    inline fun <reified T> toJsonObject(jsonStr: String): T? {

        try {
            val fromJson = builder.adapter<T>(T::class.java).fromJson(jsonStr)
            return fromJson
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    inline fun <reified T> toJsonList(jsonStr: String): List<T>? {
        try {
            val parameterizedType = Types.newParameterizedType(List::class.java, T::class.java)
            return builder.adapter<List<T>>(parameterizedType).fromJson(jsonStr)
        } catch (e: Exception) {
            println("jsonToList 异常")
            e.printStackTrace()
        }
        return null
    }


    inline fun <reified T> fromJson(jsonStr: String): T? {
        return builder.adapter<T>(T::class.java).fromJson(jsonStr)
    }


}

