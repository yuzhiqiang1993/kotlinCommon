package com.yzq.common.utils

import com.yzq.mmkv.MMKVInstance
import com.yzq.mmkv.MMKVReadWriteProp

/**
 * @description: MMKV 工具类
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2022/1/6
 * @time : 10:58 上午
 */

object MMKVUtil {

    var account: String by MMKVReadWriteProp("account", "")
    var pwd: String by MMKVReadWriteProp("pwd", "")
    var token: String by MMKVReadWriteProp("token", "")

    /**
     * 清除
     *
     * @param mmapID
     * @param cryptKey
     */
    fun clear(
        mmapID: String? = null,
        cryptKey: String? = null,
    ) {
        MMKVInstance.get(mmapID, cryptKey).clearAll()
    }

    /**
     * Remove value for key
     *
     * @param mmapID
     * @param cryptKey
     * @param key
     */
    fun removeValueForKey(
        mmapID: String? = null,
        cryptKey: String? = null,
        key: String,
    ) {
        MMKVInstance.get(mmapID, cryptKey).removeValueForKey(key)
    }

    /**
     * Remove value for keys
     *
     * @param mmapID
     * @param cryptKey
     * @param keys
     */
    fun removeValueForKeys(
        mmapID: String? = null,
        cryptKey: String? = null,
        keys: Array<String>,
    ) {
        MMKVInstance.get(mmapID, cryptKey).removeValuesForKeys(keys)
    }
}
