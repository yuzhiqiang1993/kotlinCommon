package com.yzq.aliemas.push

import android.content.Context
import com.alibaba.sdk.android.push.MessageReceiver
import com.alibaba.sdk.android.push.notification.CPushMessage
import com.blankj.utilcode.util.LogUtils

/**
 * @description: 推送广播接收器
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/1/9
 * @time   : 2:56 下午
 */

class AliPushReceiver : MessageReceiver() {
    // 消息接收部分的LOG_TAG
    val REC_TAG = "receiver"
    override fun onNotification(context: Context?, title: String, summary: String, extraMap: Map<String?, String?>) {
        // TODO处理推送通知
        LogUtils.e("Receive notification, title: $title, summary: $summary, extraMap: $extraMap")
    }

    override fun onMessage(context: Context?, cPushMessage: CPushMessage) {
        LogUtils.e(
            "onMessage, messageId: " + cPushMessage.messageId + ", title: " + cPushMessage.title + ", content:" + cPushMessage.content
        )
    }

    override fun onNotificationOpened(context: Context?, title: String, summary: String, extraMap: String) {
        LogUtils.e("MyMessageReceiver", "onNotificationOpened, title: $title, summary: $summary, extraMap:$extraMap")
    }

    override fun onNotificationClickedWithNoAction(context: Context?, title: String, summary: String, extraMap: String) {
        LogUtils.e("MyMessageReceiver", "onNotificationClickedWithNoAction, title: $title, summary: $summary, extraMap:$extraMap")
    }

    override fun onNotificationReceivedInApp(context: Context?, title: String, summary: String, extraMap: Map<String?, String?>, openType: Int, openActivity: String, openUrl: String) {
        LogUtils.e(
            "onNotificationReceivedInApp, title: $title, summary: $summary, extraMap:$extraMap, openType:$openType, openActivity:$openActivity, openUrl:$openUrl"
        )
    }

    override fun onNotificationRemoved(context: Context?, messageId: String?) {
        LogUtils.e("onNotificationRemoved")
    }
}