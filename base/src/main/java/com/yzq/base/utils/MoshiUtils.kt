package com.yzq.base.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/**
 * @description json解析工具类
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

object MoshiUtils {

    abstract class MoshiTypeReference<T> // 自定义的类，用来包装泛型

    val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    inline fun <reified T> toJson(src: T, indent: String = ""): String? {
        return kotlin.runCatching {
            val jsonAdapter = moshi.adapter<T>(getGenericType<T>())
            jsonAdapter.indent(indent).toJson(src)
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()

    }

    inline fun <reified T> toMap(src: T): Map<String, Any>? {
        return kotlin.runCatching {
            val jsonAdapter = moshi.adapter<T>(getGenericType<T>())
            val jsonStr = jsonAdapter.toJson(src)
            fromJson<Map<String, Any>>(jsonStr)
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()
    }

    inline fun <reified T> fromJson(jsonStr: String): T? = kotlin.runCatching {
        val jsonAdapter = moshi.adapter<T>(getGenericType<T>())
        jsonAdapter.fromJson(jsonStr)
    }.onFailure {
        it.printStackTrace()
    }.getOrDefault(null)


    inline fun <reified T> getGenericType(): Type {

        return object :
            MoshiTypeReference<T>() {}::class.java
            .genericSuperclass
            .let { it as ParameterizedType }
            .actualTypeArguments
            .first()
    }
}
