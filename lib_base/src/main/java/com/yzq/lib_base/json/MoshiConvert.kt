package com.yzq.lib_base.json

import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * @description: 基于moshi的json转换封装
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/3/13
 * @time   : 6:29 下午
 */

object MoshiConvert {

    val builder = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    fun toJson(src: Any): String {
        return builder.adapter(Any::class.java).toJson(src)
    }

    inline fun <reified T> fromJson(jsonStr: String): T? {
        val type = object : TypeToken<T>() {}.type
        return builder.adapter<T>(type).fromJson(jsonStr)
    }

}

