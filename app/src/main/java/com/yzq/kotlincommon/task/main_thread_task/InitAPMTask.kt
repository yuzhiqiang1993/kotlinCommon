package com.yzq.kotlincommon.task.main_thread_task

import com.alibaba.ha.adapter.*
import com.blankj.utilcode.util.AppUtils
import com.yzq.kotlincommon.config.AliEMASConfig
import com.yzq.kotlincommon.task.base.MainThreadTask
import com.yzq.lib_base.AppContext
import com.yzq.lib_base.BaseApp

/**
 * @description: 阿里 EMAS 性能分析
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/7
 * @time   : 4:46 下午
 */

class InitAPMTask : MainThreadTask() {
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
        AliHaAdapter.getInstance().addPlugin(Plugin.apm)
        AliHaAdapter.getInstance().start(config)
    }

}