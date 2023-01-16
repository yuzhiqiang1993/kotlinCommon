package com.yzq.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.blankj.utilcode.util.LogUtils
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

/**
 * @description Application基类
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/9/30
 * @time 11:02
 */

open class BaseApp : Application(), Application.ActivityLifecycleCallbacks {

    private val activityStack: Stack<Activity> = Stack()

    private val appExitListenerList: CopyOnWriteArrayList<AppExitListener> = CopyOnWriteArrayList()


    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    companion object {
        private lateinit var instance: BaseApp

        fun getInstance(): BaseApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(this)
    }

    /**
     * 主动退出App
     */
    fun exitApp() {
        activityStack.forEach {
            it.finishAfterTransition()
        }
        appExitListenerList.forEach {
            it.onAppexit()
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activityStack.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activityStack.contains(activity)) {
            activityStack.remove(activity)
        }

        if (activityStack.size <= 0) {
            exitApp()
        }
    }


    /*Appt 退出时的回调*/
    interface AppExitListener {
        fun onAppexit()
    }

    fun addAppExitListener(appExitListener: AppExitListener) {
        if (!appExitListenerList.contains(appExitListener)) {
            appExitListenerList.add(appExitListener)
        }
        LogUtils.i("appExitListenerList:${appExitListenerList.size}")
    }

    fun removeAppExitListener(appExitListener: AppExitListener) {
        if (appExitListenerList.contains(appExitListener)) {
            appExitListenerList.remove(appExitListener)
        }

        LogUtils.i("appExitListenerList:${appExitListenerList.size}")
    }
}
