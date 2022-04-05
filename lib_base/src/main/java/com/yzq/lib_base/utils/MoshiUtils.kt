package com.yzq.lib_base.utils

import com.blankj.utilcode.util.LogUtils
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType

/**
 * @description: 基于moshi的json转换封装
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/3/13
 * @time   : 6:29 下午
 */

object MoshiUtils {

    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    fun <T> toJson(adapter: JsonAdapter<T>, src: T, indent: String = ""): String {
        try {
            return adapter.indent(indent).toJson(src)
        } catch (e: Exception) {
            LogUtils.e("MoshiUtils toJson 异常:${e.localizedMessage}")
            e.printStackTrace()
        }
        return ""

    }

    /**
     * T 类型对象序列化为 json
     * @param src T
     * @param indent String
     * @return String
     */
    inline fun <reified T> toJson(src: T, indent: String = ""): String {
        val adapter = moshi.adapter(T::class.java)
        return this.toJson(adapter = adapter, src = src, indent = indent)
    }

    /**
     * 将 T 序列化为 json，指定 parameterizedType，适合复杂类型
     * @param src T
     * @param parameterizedType ParameterizedType
     * @param indent String
     * @return String
     */
    inline fun <reified T> toJson(
        src: T,
        parameterizedType: ParameterizedType,
        indent: String = ""
    ): String {
        val adapter = moshi.adapter<T>(parameterizedType)
        return this.toJson(adapter = adapter, src = src, indent = indent)

    }

    inline fun <reified T> fromJson(adapter: JsonAdapter<T>, jsonStr: String): T? {
        try {
            return adapter.fromJson(jsonStr)
        } catch (e: Exception) {
            LogUtils.e("MoshiUtils fromJson 异常:${e.localizedMessage}")
            e.printStackTrace()
        }
        return null
    }

    /**
     * json 反序列化为 T
     * @param jsonStr String
     * @return T?
     */
    inline fun <reified T> fromJson(jsonStr: String): T? {
        val adapter = moshi.adapter(T::class.java)
        return this.fromJson(adapter, jsonStr)
    }

    /**
     * json 反序列化为 T, 指定 parameterizedType，复杂数据用
     * @param jsonStr String
     * @param parameterizedType ParameterizedType
     * @return T?
     */
    inline fun <reified T> fromJson(jsonStr: String, parameterizedType: ParameterizedType): T? {
        val adapter = moshi.adapter<T>(parameterizedType)
        return this.fromJson(adapter = adapter, jsonStr = jsonStr)
    }

    /**
     * json 反序列化为 MutableList<T>
     * @param jsonStr String
     * @return MutableList<T>?
     */
    inline fun <reified T> fromJsonToList(jsonStr: String): MutableList<T>? {
        val parameterizedType = Types.newParameterizedType(MutableList::class.java, T::class.java)
        return fromJson<MutableList<T>>(jsonStr, parameterizedType)
    }

}

