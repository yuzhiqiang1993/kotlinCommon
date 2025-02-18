package com.yzq.aliemas.inittask

import com.alibaba.sdk.android.push.CloudPushService
import com.alibaba.sdk.android.push.noonesdk.PushInitConfig
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.yzq.aliemas.BuildConfig
import com.yzq.aliemas.config.AliEMASConfig
import com.yzq.application.AppManager
import com.yzq.appstartup.MainThreadTask

/**
 * @description: 初始化阿里推送
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2022/1/9
 * @time : 2:57 下午
 */

class InitAliPushTask : MainThreadTask() {

    override fun taskRun() {
        //https://help.aliyun.com/document_detail/195006.html

        val config = PushInitConfig.Builder().application(AppManager.application)
            .appKey(AliEMASConfig.appKey).appSecret(AliEMASConfig.appSecret).build()
        PushServiceFactory.init(config)
        val pushService = PushServiceFactory.getCloudPushService()
        if (BuildConfig.DEBUG) {
            pushService.setLogLevel(CloudPushService.LOG_DEBUG)
        }
    }
}
