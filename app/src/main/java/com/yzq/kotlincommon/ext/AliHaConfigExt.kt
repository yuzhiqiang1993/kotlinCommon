package com.yzq.kotlincommon.ext

import com.alibaba.ha.adapter.AliHaConfig
import com.blankj.utilcode.util.AppUtils
import com.yzq.application.AppContext
import com.yzq.application.BaseApp
import com.yzq.kotlincommon.config.AliEMASConfig

/**
 * @description: AliConfig的默认配置
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/9
 * @time   : 11:21 上午
 */

fun AliHaConfig.defaultConfig(rsaPublicKey: String = "") {

    appKey = AliEMASConfig.appKey
    appVersion = AppUtils.getAppVersionName()
    appSecret = AliEMASConfig.appSecret
    channel = "testChannel" //渠道
    userNick = null //用户昵称
    application = BaseApp.getInstance()
    context = AppContext
    isAliyunos = false //是否是是阿里云os
    if (rsaPublicKey.isNotEmpty()) {
        this.rsaPublicKey = rsaPublicKey
    }

}