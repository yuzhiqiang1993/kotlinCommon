package com.yzq.common.net.ext

import com.blankj.utilcode.util.NetworkUtils
import com.yzq.common.data.BaseResp
import com.yzq.common.net.constants.ResponseCode

/*
* 数据转换
*
* */

fun <T> BaseResp<T>.dataConvert(): T {
    if (status == ResponseCode.SUCCESS) {
        return result
    } else {
        throw Exception(message)
    }
}
