package com.yzq.mmkv

import android.os.Parcelable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @description mmkv读写委托
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/17
 * @time 10:43
 */

class MMKVReadWriteProp<V>(
    private val key: String,
    private val defauleVal: V,
    private val mmapID: String? = null,
    private val cryptKey: String? = null,
) : ReadWriteProperty<Any, V> {

//    private val mmkv by lazy {
//        if (mmapID.isEmpty()) {
//            MMKV.defaultMMKV(
//                MMKV.MULTI_PROCESS_MODE,
//                cryptKey
//            )
//        } else {
//            MMKV.mmkvWithID(
//                mmapID,
//                MMKV.MULTI_PROCESS_MODE,
//                cryptKey
//            )
//        }
//    }

    override fun getValue(thisRef: Any, property: KProperty<*>): V {
        return decodeVal(key)
    }

    /**
     * 取值
     * @param key String
     * @return V
     */
    @Suppress("UNCHECKED_CAST")
    private fun decodeVal(key: String): V {
        return MMKVInstance.get(mmapID, cryptKey).run {
            when (defauleVal) {
                is String -> decodeString(key, defauleVal)
                is Int -> decodeInt(key, defauleVal)
                is Long -> decodeLong(key, defauleVal)
                is Boolean -> decodeBool(key, defauleVal)
                is Float -> decodeFloat(key, defauleVal)
                is Double -> decodeDouble(key, defauleVal)
                is Parcelable -> decodeParcelable(key, defauleVal.javaClass)
                else -> throw IllegalArgumentException("MMKVReadWriteProp 不支持的类型")
            }
        } as V
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: V) {
        encodeval(key, value)
    }

    /**
     * 存值
     * @param key String
     * @param value V
     */
    private fun encodeval(key: String, value: V) {
        MMKVInstance.get(mmapID, cryptKey).run {
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
