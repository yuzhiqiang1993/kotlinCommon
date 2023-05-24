package com.yzq.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.blankj.utilcode.util.LogUtils
import java.util.Stack
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

/**
 * @description Application基类
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/9/30
 * @time 11:02
 */

open class BaseApp : Application(), Application.ActivityLifecycleCallbacks {

    /*存放Activity的Stack*/
    private val activityStack: Stack<Activity> = Stack()

    /**/
    private val activityCount = AtomicInteger(0)

    /*App是否处于前台*/
    private val isForeground = AtomicBoolean(false)

    /*App状态监听*/
    private val appStateListenerList: CopyOnWriteArrayList<AppStateListener> =
        CopyOnWriteArrayList()


    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    companion object {
        const val TAG = "BaseApp"
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
        appStateListenerList.forEach {
            it.onAppExit()
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        LogUtils.iTag(TAG, "onActivityStarted")
        activityStack.add(activity)
        activityCount.incrementAndGet()//自增
        if (!isForeground.get()) {
            LogUtils.iTag(TAG, "onAppForeground")
            isForeground.compareAndSet(false, true)
            appStateListenerList.forEach {
                it.onAppForeground()
            }
        }

    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        activityCount.decrementAndGet()//自减
        if (activityCount.get() <= 0) {
            /*说明此时应用处于后台*/
            isForeground.set(false)
            LogUtils.iTag(TAG, "onAppBackground")
            appStateListenerList.forEach {
                it.onAppBackground()
            }
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activityStack.contains(activity)) {
            activityStack.remove(activity)
        }
        if (activityCount.get() <= 0) {
            LogUtils.iTag(TAG, "onAppExit")
            exitApp()
        }
    }


    /*Appt 退出时的回调*/
    interface AppStateListener {
        fun onAppForeground() {}
        fun onAppBackground() {}
        fun onAppExit() {}
    }

    fun addAppStateListener(appStateListener: AppStateListener) {
        if (!appStateListenerList.contains(appStateListener)) {
            appStateListenerList.add(appStateListener)
        }
        LogUtils.i("appExitListenerList:${appStateListenerList.size}")
    }

    fun removeAppExitListener(appExitListener: AppStateListener) {
        if (appStateListenerList.contains(appExitListener)) {
            appStateListenerList.remove(appExitListener)
        }

        LogUtils.i("appExitListenerList:${appStateListenerList.size}")
    }
}
