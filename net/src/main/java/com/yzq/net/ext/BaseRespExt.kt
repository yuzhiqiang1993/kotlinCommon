package com.yzq.net.ext

import com.yzq.data.base.BaseResp
import com.yzq.net.constants.ResponseCode
import com.yzq.net.core.ApiThrowable

/**
 * @description 接口数据转换
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

fun <T> BaseResp<T>.dataConvert(): T? {
    if (code == ResponseCode.SUCCESS) {
        return data
    } else {
        throw ApiThrowable(code, message)
    }
}
