package com.yzq.aliemas.inittask

import com.alibaba.ha.adapter.AliHaAdapter
import com.alibaba.ha.adapter.AliHaConfig
import com.alibaba.ha.adapter.Plugin
import com.yzq.aliemas.config.AliEMASConfig
import com.yzq.aliemas.ext.defaultConfig
import com.yzq.appstartup.MainThreadTask

/**
 * @description: 阿里 EMAS 性能分析
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2022/1/7
 * @time : 4:46 下午
 */

class InitAPMTask : MainThreadTask() {
    override fun taskRun() {

        val config = AliHaConfig()
        config.defaultConfig(AliEMASConfig.appmonitorRsaSecret)
        /*性能分析 https://help.aliyun.com/document_detail/164719.htm?spm=a2c4g.11186623.0.0.227d186a2dMjha*/
        AliHaAdapter.getInstance().addPlugin(Plugin.apm)
        AliHaAdapter.getInstance().start(config)
    }
}
