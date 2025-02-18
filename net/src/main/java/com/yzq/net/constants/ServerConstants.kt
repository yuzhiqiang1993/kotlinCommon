package com.yzq.net.constants

import com.yzq.coroutine.BuildConfig


/**
 * @description: 服务器相关常量
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 18:06
 *
 */

object ServerConstants {

    private const val SERVER_URL_RELEASE = "http://v.juhe.cn/"

    private const val SERVER_URL_DEBUG = "http://v.juhe.cn/"

    private const val API = "v2/movie/"


    //服务器地址
    val serverUrl: String
        get() {
            return if (BuildConfig.DEBUG) SERVER_URL_DEBUG else SERVER_URL_RELEASE

        }


    //接口地址
    val apiUrl: String
        get() {
            return if (BuildConfig.DEBUG) "$SERVER_URL_DEBUG$API" else "$SERVER_URL_RELEASE$API"

        }
}


