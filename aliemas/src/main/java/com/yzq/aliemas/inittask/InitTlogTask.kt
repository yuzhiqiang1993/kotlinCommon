package com.yzq.aliemas.inittask

import com.alibaba.ha.adapter.AliHaAdapter
import com.alibaba.ha.adapter.AliHaConfig
import com.alibaba.ha.adapter.Plugin
import com.alibaba.ha.adapter.service.tlog.TLogLevel
import com.alibaba.ha.adapter.service.tlog.TLogService
import com.yzq.aliemas.config.AliEMASConfig
import com.yzq.aliemas.ext.defaultConfig
import com.yzq.base.startup.base.MainThreadTask

/**
 * @description: 远程日志   https://emas.console.aliyun.com/?spm=5176.12818093.ProductAndService--ali--widget-home-product-recent.dre0.5adc16d01g8kGt#/workspace/3712117/tlog/28128416/2
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/9
 * @time   : 11:39 上午
 */

class InitTlogTask : MainThreadTask() {
    override fun taskRun() {
        val config = AliHaConfig()
        config.defaultConfig(AliEMASConfig.tlogRsaSecret)

        AliHaAdapter.getInstance().addPlugin(Plugin.tlog)
        AliHaAdapter.getInstance().openDebug(true)
        AliHaAdapter.getInstance().start(config)
        TLogService.updateLogLevel(TLogLevel.INFO) //配置项：控制台可拉取的日志级别

    }
}