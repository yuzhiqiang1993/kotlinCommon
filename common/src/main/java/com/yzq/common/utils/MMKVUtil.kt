package com.yzq.common.utils

import com.yzq.base.delegate.MMKVReadWriteProp

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
}
