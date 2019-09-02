package com.yzq.lib_base.extend

import android.annotation.SuppressLint
import android.content.Context
import com.blankj.utilcode.util.AppUtils
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * @description: SharedPreferences扩展函数
 * @author : yzq
 * @date   : 2018/12/20
 * @time   : 16:04
 *
 */
class SharedPreference<T>(val name: String, val defaultVal: T) : ReadWriteProperty<Any?, T> {


    private val prfs by lazy {

        // LogUtils.i("prfs lazy ->${AppUtils.getAppPackageName()}")
        com.yzq.lib_base.AppContext.getSharedPreferences(AppUtils.getAppPackageName(), Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getPreference(name)
    }


    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    private fun getPreference(key: String): T {
        return with(prfs) {
            when (defaultVal) {
                is String -> getString(key, defaultVal)
                is Int -> getInt(key, defaultVal)
                is Boolean -> getBoolean(key, defaultVal)
                is Float -> getFloat(key, defaultVal)
                is Long -> getLong(key, defaultVal)
                else -> throw  IllegalArgumentException("unsupported type")
            } as T
        }


    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {

        putPreference(name, value)

    }


    @SuppressLint("CommitPrefEdits")
    private fun putPreference(key: String, value: T) {

        with(prfs.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> throw  IllegalArgumentException("unsupported type")
            }
        }.apply()
    }


}