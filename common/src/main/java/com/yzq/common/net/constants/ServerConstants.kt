package com.yzq.common.net.constants

import com.yzq.common.BuildConfig


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


    const val DEVICE_ID = "DeviceId"
    const val AES_KEY = "aesKey"

    const val RSA_PUB_KEY =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIarYvrIMZGHKa8f2E6ubg0//28R1zJ4ArD+XELXYvDrM8UBR42PqJCpjPN3hC91YAnnk2Y9U+X5o/rGxH5ZTZzYy+rkAmZFJa1fK2mWDxPYJoxH+DGHQc+h8t83BMB4pKqVPhcJVF6Ie+qpD5RFUU/e5iEz8ZZFDroVE3ubKaKwIDAQAB"


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


