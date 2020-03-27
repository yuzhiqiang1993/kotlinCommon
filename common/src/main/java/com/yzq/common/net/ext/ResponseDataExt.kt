package com.yzq.common.net.ext

import com.yzq.common.net.constants.ResponseCode
import com.yzq.common.data.BaseResp

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
