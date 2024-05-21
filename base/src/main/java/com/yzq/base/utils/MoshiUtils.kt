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

    inline fun <reified T> toJson(src: T, indent: String = ""): String {
        val jsonAdapter = moshi.adapter<T>(getGenericType<T>())
        return jsonAdapter.indent(indent).toJson(src)
    }

    inline fun <reified T> toMap(src: T): Map<String, Any>? {
        val jsonAdapter = moshi.adapter<T>(getGenericType<T>())
        val jsonStr = jsonAdapter.toJson(src)
        return fromJson<Map<String, Any>>(jsonStr)
    }

    inline fun <reified T> fromJson(jsonStr: String): T? {
        val jsonAdapter = moshi.adapter<T>(getGenericType<T>())
        return jsonAdapter.fromJson(jsonStr)
    }

    inline fun <reified T> getGenericType(): Type {
        return object :
            MoshiTypeReference<T>() {}::class.java
            .genericSuperclass
            .let { it as ParameterizedType }
            .actualTypeArguments
            .first()
    }
}
