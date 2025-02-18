package com.yzq.data.base


/**
 * @Description: 服务端返回数据基类
 * @author : yzq
 * @date : 2018/7/2
 * @time : 14:11
 *
 */
data class BaseResp<T>(
    var code: Int = 0, // 0
    var data: T? = null,
    var message: String = "", // 成功的返回
)
