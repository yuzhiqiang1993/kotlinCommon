package com.yzq.kotlincommon.task.main_thread_task

import com.alibaba.sdk.android.push.CloudPushService
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.noonesdk.PushInitConfig
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.blankj.utilcode.util.LogUtils
import com.yzq.application.AppContext
import com.yzq.application.BaseApp
import com.yzq.base.startup.base.MainThreadTask
import com.yzq.kotlincommon.BuildConfig
import com.yzq.kotlincommon.config.AliEMASConfig

/**
 * @description: 初始化阿里推送
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/9
 * @time   : 2:57 下午
 */

class InitAliPushTask : MainThreadTask() {

    private val TAG = javaClass.canonicalName
    override fun taskRun() {
        /*https://help.aliyun.com/document_detail/195006.html*/

        val config = PushInitConfig.Builder().application(BaseApp.getInstance())
            .appKey(AliEMASConfig.appKey)
            .appSecret(AliEMASConfig.appSecret)
            .build()
        PushServiceFactory.init(config)
        val pushService = PushServiceFactory.getCloudPushService()
        if (BuildConfig.DEBUG) {
            pushService.setLogLevel(CloudPushService.LOG_DEBUG)
        }

        /*该操作会获取用户信息  所以建议在获取相关权限后再初始化 同时还能提升一些启动速度*/
        pushService.register(AppContext, object : CommonCallback {
            override fun onSuccess(response: String?) {
                LogUtils.i("init cloudchannel success")
            }

            override fun onFailed(errorCode: String, errorMessage: String) {
                LogUtils.i("init cloudchannel failed -- errorcode:$errorCode -- errorMessage:$errorMessage")
            }
        })

    }
}