package com.yzq.aliemas.ext

import com.alibaba.ha.adapter.AliHaConfig
import com.yzq.aliemas.config.AliEMASConfig
import com.yzq.application.AppContext
import com.yzq.application.AppManager
import com.yzq.util.ext.getAppVersionName

/**
 * @description: AliConfig的默认配置
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2022/1/9
 * @time : 11:21 上午
 */

fun AliHaConfig.defaultConfig(rsaPublicKey: String = "") {

    appKey = AliEMASConfig.appKey
    appVersion = getAppVersionName() ?: ""
    appSecret = AliEMASConfig.appSecret
    channel = "channel" // 渠道
    userNick = "xeon" // 用户昵称
    application = AppManager.application
    context = AppContext
    isAliyunos = false // 是否是是阿里云os
    if (rsaPublicKey.isNotEmpty()) {
        this.rsaPublicKey = rsaPublicKey
    }
}
