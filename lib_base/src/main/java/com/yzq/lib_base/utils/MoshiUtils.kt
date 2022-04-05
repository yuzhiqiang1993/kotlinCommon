package com.yzq.lib_base.utils

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

    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    fun toJson(src: Any, indent: String = " "): String {
        return moshi.adapter(Any::class.java).indent(indent).toJson(src)
    }

    inline fun <reified T> fromJson(jsonStr: String): T? {

        try {
            val fromJson = moshi.adapter<T>(T::class.java).fromJson(jsonStr)
            return fromJson
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    inline fun <reified T> fromJsonArr(jsonStr: String): List<T>? {
        try {
            val parameterizedType = Types.newParameterizedType(List::class.java, T::class.java)
            return moshi.adapter<List<T>>(parameterizedType).fromJson(jsonStr)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}

