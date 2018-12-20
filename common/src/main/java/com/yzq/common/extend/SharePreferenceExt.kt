package com.yzq.common.extend

import android.content.Context
import com.blankj.utilcode.util.AppUtils
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * @description: SharePreference的扩展
 * @author : yzq
 * @date   : 2018/12/20
 * @time   : 16:04
 *
 */
class Preference<T>(val context: Context, val name: String, val defaultVal: T) : ReadWriteProperty<Any?, T> {


    private val prfs by lazy {

        // LogUtils.i("prfs lazy ->${AppUtils.getAppPackageName()}")
        context.getSharedPreferences(AppUtils.getAppPackageName(), Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getPreference(name)
    }


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