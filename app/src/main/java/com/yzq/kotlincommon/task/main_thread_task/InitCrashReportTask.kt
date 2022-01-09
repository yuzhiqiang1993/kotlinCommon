package com.yzq.kotlincommon.task.main_thread_task

import com.alibaba.ha.adapter.*
import com.yzq.kotlincommon.ext.defaultConfig
import com.yzq.lib_base.startup.base.MainThreadTask

/**
 * @description: 崩溃分析 https://emas.console.aliyun.com/?spm=5176.12818093.ProductAndService--ali--widget-home-product-recent.dre6.4c4b16d0ujeByd#/workspace/3712117/mca/28128416/2
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/9
 * @time   : 11:35 上午
 */

class InitCrashReportTask : MainThreadTask() {
    override fun taskRun() {
        val config = AliHaConfig()
        config.defaultConfig()
        /*崩溃日志  https://help.aliyun.com/document_detail/93932.htm*/
        AliHaAdapter.getInstance().addPlugin(Plugin.crashreporter)
        AliHaAdapter.getInstance().start(config)
    }
}