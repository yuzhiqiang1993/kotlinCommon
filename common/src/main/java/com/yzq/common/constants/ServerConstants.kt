package com.yzq.common.constants

import com.yzq.common.BuildConfig


/**
 * @description: 服务器相关常量
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 18:06
 *
 */

class ServerConstants {

    companion object {
        private val SERVER_URL_RELEASE = "http://v.juhe.cn/"
        private val SERVER_URL_DEBUG = "http://v.juhe.cn/"
        private val API = "toutiao/"


        val DEVICE_ID = "DeviceId"
        val AES_KEY = "aesKey"


        /*获取服务器地址*/
        fun getServerUrl(): String {
            return if (BuildConfig.DEBUG) SERVER_URL_DEBUG else SERVER_URL_RELEASE

        }

        /*获取接口地址*/
        fun getApiUrl(): String {
            return if (BuildConfig.DEBUG) SERVER_URL_DEBUG + API else SERVER_URL_RELEASE + API

        }
    }


}