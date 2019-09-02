package com.yzq.common.utils

import com.yzq.lib_base.extend.SharedPreference


/**
 * @description: 管理本地存储的sp
 * @author : yzq
 * @date   : 2018/11/6
 * @time   : 15:23
 *
 */

object LocalSpUtils {
    var account: String by SharedPreference("account", "")
    var pwd: String by SharedPreference("pwd", "")
}