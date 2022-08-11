package com.yzq.kotlincommon.task.main_thread_task

import com.alibaba.sdk.android.man.MANServiceProvider
import com.blankj.utilcode.util.AppUtils
import com.yzq.kotlincommon.config.AliEMASConfig
import com.yzq.lib_base.AppContext
import com.yzq.lib_base.BaseApp
import com.yzq.lib_base.startup.base.MainThreadTask

/**
 * @description: 移动分析
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/9
 * @time   : 1:45 下午
 */

class InitMobileAnalyticsTask : MainThreadTask() {
    override fun taskRun() {

        /**
         * 初始化Mobile Analytics服务
         * https://help.aliyun.com/document_detail/30037.htm?spm=a2c4g.11186623.0.0.4d72273fRPQID6
         */

        // 获取MAN服务
        val manService = MANServiceProvider.getService()

        // 打开调试日志
        manService.manAnalytics.turnOnDebug()

        // MAN另一初始化方法，手动指定appKey和appSecret

        manService.manAnalytics.init(
            BaseApp.INSTANCE,
            AppContext,
            AliEMASConfig.appKey,
            AliEMASConfig.appSecret
        )

        // 若需要关闭 SDK 的自动异常捕获功能可进行如下操作,详见文档5.4
//        manService.manAnalytics.turnOffCrashReporter()

        // 通过此接口关闭页面自动打点功能，详见文档4.2
        //manService.getMANAnalytics().turnOffAutoPageTrack();
        // 设置渠道（用以标记该app的分发渠道名称），如果不关心可以不设置即不调用该接口，渠道设置将影响控制台【渠道分析】栏目的报表展现。如果文档3.3章节更能满足您渠道配置的需求，就不要调用此方法，按照3.3进行配置即可
//        manService.manAnalytics.setChannel("某渠道")

        // 若AndroidManifest.xml 中的 android:versionName 不能满足需求，可在此指定；
        // 若既没有设置AndroidManifest.xml 中的 android:versionName，也没有调用setAppVersion，appVersion则为null
        manService.manAnalytics.setAppVersion(AppUtils.getAppVersionName())
    }
}