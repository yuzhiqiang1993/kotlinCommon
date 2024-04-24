package com.yzq.base.extend

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * 获取当前时间字符串
 * @param pattern String
 * @return String
 */
fun getNowString(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date())
}