package com.yzq.mmkv

import com.tencent.mmkv.MMKV

/**
 * @description MMKV实例
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/17
 * @time 11:12
 */

object MMKVInstance {
    fun get(
        mmapID: String? = null,
        cryptKey: String? = null,
    ): MMKV {
        return if (mmapID.isNullOrEmpty()) {
            MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, cryptKey)
        } else {
            MMKV.mmkvWithID(mmapID, MMKV.MULTI_PROCESS_MODE, cryptKey)
        }
    }
}
