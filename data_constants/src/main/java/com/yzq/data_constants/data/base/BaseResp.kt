package com.yzq.data_constants.data.base

import com.google.gson.annotations.SerializedName


/**
 * @Description: 服务端返回数据基类
 * @author : yzq
 * @date   : 2018/7/2
 * @time   : 14:11
 *
 */
data class BaseResp<T>(
        var reason: String = "", // 成功的返回
        var result: T,
        @SerializedName("error_code")
        var errorCode: Int = 0 // 0
)