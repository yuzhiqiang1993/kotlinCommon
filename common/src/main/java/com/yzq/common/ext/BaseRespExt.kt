package com.yzq.common.ext

import com.squareup.moshi.Types
import com.yzq.common.data.BaseResp
import com.yzq.common.net.constants.ResponseCode
import com.yzq.lib_base.utils.MoshiUtils

fun <T> BaseResp<T>?.dataConvert(): T {
    if (this == null) {
        throw Exception("BaseResp can not be null")
    }
    if (code == ResponseCode.SUCCESS) {
        return data
    } else {
        throw Exception(message)
    }
}


inline fun <reified T> BaseResp<T>.toJson(indent: String = ""): String {
    val newParameterizedType = Types.newParameterizedType(BaseResp::class.java, T::class.java)
    return MoshiUtils.toJson(this, newParameterizedType, indent)
}

inline fun <reified T> String.toBaseResp(): BaseResp<T>? {
    val newParameterizedType = Types.newParameterizedType(BaseResp::class.java, T::class.java)
    return MoshiUtils.fromJson<BaseResp<T>>(this, newParameterizedType)
}

inline fun <reified T> String.toBaseRespList(): BaseResp<MutableList<T>>? {
    val insideType = Types.newParameterizedType(List::class.java, T::class.java)
    val parameterizedType = Types.newParameterizedType(BaseResp::class.java, insideType)
    return MoshiUtils.fromJson<BaseResp<MutableList<T>>>(this, parameterizedType)
}