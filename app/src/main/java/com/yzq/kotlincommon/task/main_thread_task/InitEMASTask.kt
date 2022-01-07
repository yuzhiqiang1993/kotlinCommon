package com.yzq.kotlincommon.task.main_thread_task

import com.alibaba.ha.adapter.*
import com.blankj.utilcode.util.AppUtils
import com.yzq.kotlincommon.config.AliEMASConfig
import com.yzq.kotlincommon.task.base.MainThreadTask
import com.yzq.lib_base.AppContext
import com.yzq.lib_base.BaseApp

/**
 * @description: 阿里 EMAS 平台 https://help.aliyun.com/document_detail/68655.html?spm=5176.13194971.help.dexternal.626acb3cjEXaQr
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/7
 * @time   : 4:46 下午
 */

class InitEMASTask : MainThreadTask() {
    override fun taskRun() {

        val config = AliHaConfig()
        config.appKey = AliEMASConfig.appKey
        config.appVersion = AppUtils.getAppVersionName()
        config.appSecret = AliEMASConfig.appSecret
        config.channel = "testChannel" //渠道
        config.userNick = null //用户昵称
        config.application = BaseApp.INSTANCE
        config.context = AppContext
        config.isAliyunos = false //是否是是阿里云os
        config.rsaPublicKey = AliEMASConfig.appmonitorRsaSecret

        /*性能分析 https://help.aliyun.com/document_detail/164719.htm?spm=a2c4g.11186623.0.0.227d186a2dMjha*/
        AliHaAdapter.getInstance().addPlugin(Plugin.apm)
        /*崩溃日志  https://help.aliyun.com/document_detail/93932.htm*/
        AliHaAdapter.getInstance().addPlugin(Plugin.crashreporter)
        AliHaAdapter.getInstance().start(config)
    }

}