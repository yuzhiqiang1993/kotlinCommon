package com.yzq.common.utils

import android.os.Parcelable
import android.text.TextUtils
import com.tencent.mmkv.MMKV
import com.yzq.lib_base.extend.MMKVReadWriteProp

/**
 * @description: MMKV 工具类
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/6
 * @time   : 10:58 上午
 */

object MMKVUtil {

    var account: String by MMKVReadWriteProp("account", "")
    var pwd: String by MMKVReadWriteProp("pwd", "")
    var token: String by MMKVReadWriteProp("token", "")

    private fun getMMKV(mmapID: String = ""): MMKV {
        return if (TextUtils.isEmpty(mmapID)) {
            MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null)
        } else {
            MMKV.mmkvWithID(mmapID, MMKV.MULTI_PROCESS_MODE, null)
        }
    }

    private fun put(key: String, value: Any, mmkvID: String = "") {
        val mmkv = getMMKV(mmkvID)

        when (value) {
            is String -> {
                mmkv.putString(key, value)
            }
            is Int -> {
                mmkv.putInt(key, value)
            }
            is Long -> {
                mmkv.putLong(key, value)
            }
            is Boolean -> {
                mmkv.putBoolean(key, value)
            }
            is Float -> {
                mmkv.putFloat(key, value)
            }
            is Double -> {
                mmkv.encode(key, value)
            }
            is Parcelable -> {
                mmkv.encode(key, value)
            }
            else -> {
                throw IllegalArgumentException("不支持的参数类型")
            }
        }

    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    private fun <T> get(key: String, defaultValue: T, mmapID: String = ""): T {
        val mmkv = getMMKV(mmapID)

        return mmkv.run {
            when (defaultValue) {
                is String -> {
                    decodeString(key, defaultValue)
                }
                is Int -> {
                    decodeInt(key, defaultValue)
                }
                is Long -> {
                    decodeLong(key, defaultValue)
                }
                is Boolean -> {
                    decodeBool(key, defaultValue)
                }
                is Float -> {
                    decodeFloat(key, defaultValue)
                }
                is Double -> {
                    decodeDouble(key, defaultValue)
                }
                is Parcelable -> {
                    decodeParcelable(key, defaultValue.javaClass)
                }
                else -> {
                    defaultValue
                }

            } as T

        }

    }

}