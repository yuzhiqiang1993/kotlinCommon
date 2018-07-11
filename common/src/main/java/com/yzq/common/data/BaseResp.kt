package com.yzq.common.data


/**
 * @Description: 服务端返回数据基类
 * @author : yzq
 * @date   : 2018/7/2
 * @time   : 14:11
 *
 */

data class BaseResp<T>(var fig: Int, var message: String, var data: T)