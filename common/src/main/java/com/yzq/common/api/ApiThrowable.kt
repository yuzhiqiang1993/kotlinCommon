package com.yzq.common.api


/**
 * @description 接口异常
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/10/28
 * @time    16:38
 */

class ApiThrowable(val code: Int, msg: String) : Throwable(msg)