package com.yzq.common.data.moshi

/**
 * @description: 走网络请求的测试数据，启动 kotlin_spring_maven
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/4/7
 * @time   : 12:24
 */

data class LocalUser(
    val age: Int,
    val createTime: String,
    val gender: String,
    val id: Int,
    val name: String,
    val updateTime: String
)