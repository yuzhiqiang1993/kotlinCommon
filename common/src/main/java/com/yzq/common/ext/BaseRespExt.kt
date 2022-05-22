package com.yzq.common.ext

import com.yzq.common.data.BaseResp
import com.yzq.common.net.constants.ResponseCode

fun <T> BaseResp<T>?.dataConvert(): T {
    if (this == null) {
        throw Exception("BaseResp can not be null")
    }
    if (code == ResponseCode.SUCCESS) {
        return data
    } else {
        throw Exception(message)
    }
}
