package com.yzq.common.utils

import com.yzq.common.AppContext
import com.yzq.common.extend.Preference


/**
 * @description: 管理本地存储的sp
 * @author : yzq
 * @date   : 2018/11/6
 * @time   : 15:23
 *
 */

object LocalSpUtils {
    var account: String by Preference(AppContext, "account", "")
    var pwd: String by Preference(AppContext, "pwd", "")


}