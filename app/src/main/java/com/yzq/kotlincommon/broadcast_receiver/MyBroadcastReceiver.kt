package com.yzq.kotlincommon.broadcast_receiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.therouter.TheRouter
import com.therouter.router.Navigator
import com.therouter.router.interceptor.NavigationCallback
import com.yzq.application.AppManager
import com.yzq.common.constants.RoutePath
import com.yzq.logger.Logger


/**
 * @description: 自定义广播接收器
 * @author : yuzhiqiang
 */

class MyBroadcastReceiver : BroadcastReceiver() {

    //测试广播命令：adb shell am broadcast -a com.yzq.ACTION_TEST -n com.yzq.kotlincommon/.broadcast_receiver.MyBroadcastReceiver --es key1 value1 --ei key2 123 --ez key3 true

    private val TAG = "MyBroadcastReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {
        Logger.it(TAG, "onReceive")
        if (intent?.action == "com.yzq.ACTION_TEST") {
            val value1 = intent.getStringExtra("key1")
            val value2 = intent.getIntExtra("key2", -1)
            val value3 = intent.getBooleanExtra("key3", false)

            Logger.it(TAG, "key1:${value1}", "key2:${value2}", "key3:${value3}")


            val activityCount = AppManager.activityCount

            Logger.it(TAG, "activityCount:${activityCount}")

            if (activityCount == 0) {
                Logger.et(TAG, "App 未运行")
            }

            TheRouter.build(RoutePath.Main.COMPOSE)
                .navigation(context, object : NavigationCallback() {
                    override fun onFound(navigator: Navigator) {
                        super.onFound(navigator)
                        Logger.it(TAG, "onFound:${navigator.url}")
                    }

                    override fun onArrival(navigator: Navigator) {
                        super.onArrival(navigator)
                        Logger.it(TAG, "onArrival")
                    }

                    override fun onActivityCreated(navigator: Navigator, activity: Activity) {
                        super.onActivityCreated(navigator, activity)
                        Logger.it(TAG, "onActivityCreated")
                    }

                    override fun onLost(navigator: Navigator, requestCode: Int) {
                        super.onLost(navigator, requestCode)
                        Logger.it(TAG, "onLost")
                    }
                })
        }
    }
}