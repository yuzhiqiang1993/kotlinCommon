package com.yzq.lib_base.delegate

import android.os.Parcelable
import android.text.TextUtils
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @description: mmkv 读写属性委托
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/6
 * @time   : 2:42 下午
 */

class MMKVReadWriteProp<V>(
    private val key: String,
    private val defauleVal: V,
    mmapID: String = ""
) : ReadWriteProperty<Any?, V> {

    private val mmkv by lazy {
        if (TextUtils.isEmpty(mmapID)) MMKV.defaultMMKV(
            MMKV.MULTI_PROCESS_MODE,
            null
        ) else MMKV.mmkvWithID(
            mmapID,
            MMKV.MULTI_PROCESS_MODE,
            null
        )
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): V {
        return decodeVal(key)
    }

    /**
     * 取值
     * @param key String
     * @return V
     */
    @Suppress("UNCHECKED_CAST")
    private fun decodeVal(key: String): V {
        return mmkv.run {
            when (defauleVal) {
                is String -> mmkv.decodeString(key, defauleVal)
                is Int -> mmkv.decodeInt(key, defauleVal)
                is Long -> mmkv.decodeLong(key, defauleVal)
                is Boolean -> mmkv.decodeBool(key, defauleVal)
                is Float -> mmkv.decodeFloat(key, defauleVal)
                is Double -> mmkv.decodeDouble(key, defauleVal)
                is Parcelable -> decodeParcelable(key, defauleVal.javaClass)
                else -> throw  IllegalArgumentException("MMKVReadWriteProp 不支持的类型")
            }
        } as V
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        encodeval(key, value)
    }

    /**
     * 存值
     * @param key String
     * @param value V
     */
    private fun encodeval(key: String, value: V) {
        mmkv.run {
            when (value) {
                is String -> encode(key, value)
                is Int -> encode(key, value)
                is Long -> encode(key, value)
                is Boolean -> encode(key, value)
                is Float -> encode(key, value)
                is Double -> encode(key, value)
                is Parcelable -> encode(key, value)
                else -> throw IllegalArgumentException("MMKVReadWriteProp 不支持的类型")
            }
        }
    }

}