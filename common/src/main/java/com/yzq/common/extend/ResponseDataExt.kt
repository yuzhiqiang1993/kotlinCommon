package com.yzq.common.extend

import com.yzq.common.constants.ResponseCode
import com.yzq.common.data.BaseResp
import io.reactivex.Single

/*
* 数据转换
*
* */
fun <T> Single<BaseResp<T>>.dataConvert(): Single<T> {
    return flatMap { baseResp ->
        if (baseResp.errorCode == ResponseCode.SUCCESS) Single.just(baseResp.result) else Single.error(
            Throwable(message = baseResp.reason)
        )
    }
}
