package com.yzq.common.ext

import com.yzq.common.api.ApiThrowable
import com.yzq.common.api.BaseResp
import com.yzq.common.net.constants.ResponseCode


/**
 * @description 接口数据转换
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/10/29
 * @time    17:36
 */

fun <T> BaseResp<T>?.dataConvert(): T? {
    if (this == null) {
        throw Exception("BaseResp can not be null")
    }
    if (code == ResponseCode.SUCCESS) {
        return data
    } else {
        throw ApiThrowable(code, message)
    }
}
