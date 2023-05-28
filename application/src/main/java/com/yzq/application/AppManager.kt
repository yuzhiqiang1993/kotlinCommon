package com.yzq.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import java.util.Stack
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

/**
 * 用于管理App中的Activity
 */
object AppManager : Application.ActivityLifecycleCallbacks {

    const val TAG = "AppManager"

    private val initialized = AtomicBoolean(false)

    /*存放Activity的Stack*/
    private val activityStack: Stack<Activity> = Stack()

    /*ActivityCount*/
    private val _activityCount = AtomicInteger(0)

    /*Activity的数量*/
    private val activityCount
        get() = _activityCount.get()

    private val topActivity: Activity?
        get() {
            return if (activityStack.size > 0) {
                activityStack[0]
            } else {
                null
            }
        }

    /*App是否处于前台*/
    private val _isForeground = AtomicBoolean(false)

    /*是否处于前台*/
    val isForeground: Boolean
        get() = _isForeground.get()


    /*App状态监听*/
    private val appStateListenerList: CopyOnWriteArrayList<AppStateListener> =
        CopyOnWriteArrayList()

    fun init(application: Application) {
        if (initialized.get()) {
            LogUtils.iTag(TAG, "已经初始化过")
            return
        }
        application.registerActivityLifecycleCallbacks(this)
    }

    fun addAppStateListener(appStateListener: AppStateListener) {
        if (!appStateListenerList.contains(appStateListener)) {
            appStateListenerList.add(appStateListener)
        }
        LogUtils.iTag(TAG, "appStateListenerList:${appStateListenerList.size}")
    }

    fun removeAppStateListener(appStateListener: AppStateListener) {
        if (appStateListenerList.contains(appStateListener)) {
            appStateListenerList.remove(appStateListener)
        }

        LogUtils.iTag(TAG, "appStateListenerList:${appStateListenerList.size}")
    }

    fun clearAppStateListener() {
        appStateListenerList.clear()
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        LogUtils.iTag(TAG, "onActivityStarted")
        activityStack.add(activity)
        _activityCount.incrementAndGet()//自增
        if (!_isForeground.get()) {
            LogUtils.iTag(TAG, "onAppForeground")
            _isForeground.compareAndSet(false, true)
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
        _activityCount.decrementAndGet()//自减
        if (_activityCount.get() <= 0) {
            /*说明此时应用处于后台*/
            _isForeground.set(false)
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
        if (_activityCount.get() <= 0) {
            LogUtils.iTag(TAG, "onAppExit")
            appStateListenerList.forEach {
                it.onAppExit()
            }
        }
    }

    fun exitApp() {
        activityStack.forEach {
            it.finishAfterTransition()
        }
    }
}