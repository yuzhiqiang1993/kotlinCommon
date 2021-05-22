package com.yzq.common.data


/**
 * @Description: 服务端返回数据基类
 * @author : yzq
 * @date   : 2018/7/2
 * @time   : 14:11
 *
 */
data class BaseResp<T>(
    var message: String = "", // 成功的返回
    var result: T,
    var status: Int = 0 // 0
) 