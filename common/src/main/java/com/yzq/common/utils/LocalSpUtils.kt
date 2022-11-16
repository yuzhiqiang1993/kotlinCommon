package com.yzq.common.utils

import com.yzq.base.extend.SharedPreferenceProp

/**
 * @description: 管理本地存储的sp
 * @author : yzq
 * @date : 2018/11/6
 * @time : 15:23
 *
 */

object LocalSpUtils {
    var account: String by SharedPreferenceProp("account", "")
    var pwd: String by SharedPreferenceProp("pwd", "")
    var token: String by SharedPreferenceProp("token", "")
}
