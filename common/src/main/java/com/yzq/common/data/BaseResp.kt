package com.yzq.common.data

import com.yzq.common.net.constants.ResponseCode


/**
 * @Description: 服务端返回数据基类
 * @author : yzq
 * @date   : 2018/7/2
 * @time   : 14:11
 *
 */
data class BaseResp<T>(
    var code: Int = ResponseCode.SUCCESS, // 0
    var data: T,
    var message: String = "", // 成功的返回
)