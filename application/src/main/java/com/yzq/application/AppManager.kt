package com.yzq.application

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.yzq.logger.LogCat
import java.util.Stack
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger


/**
 * @description AppManager
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

object AppManager : Application.ActivityLifecycleCallbacks {

    const val TAG = "AppManager"

    private val initialized = AtomicBoolean(false)

    /*存放Activity的Stack*/
    private val activityStack: Stack<Activity> = Stack()

    /*ActivityCount*/
    private val _activityCount = AtomicInteger(0)

    /*Activity的数量*/
    val activityCount
        get() = _activityCount.get()

    val topActivity: Activity?
        get() {
            return if (activityStack.size > 0) {
                activityStack[0]
            } else {
                null
            }
        }


    private val _isForeground = AtomicBoolean(false)
    val isForeground: Boolean
        get() = _isForeground.get()


    /*App状态监听*/
    private val appStateListenerList: CopyOnWriteArrayList<AppStateListener> =
        CopyOnWriteArrayList()


    fun init(application: Application) {
        if (initialized.get()) {
            LogCat.i("已经初始化过")
            return
        }
        application.registerActivityLifecycleCallbacks(this)
    }

    fun addAppStateListener(appStateListener: AppStateListener) {
        if (!appStateListenerList.contains(appStateListener)) {
            appStateListenerList.add(appStateListener)
        }
        LogCat.i("appStateListenerList:${appStateListenerList.size}")
    }

    fun removeAppStateListener(appStateListener: AppStateListener) {
        if (appStateListenerList.contains(appStateListener)) {
            appStateListenerList.remove(appStateListener)
        }

        LogCat.i("appStateListenerList:${appStateListenerList.size}")
    }

    fun clearAppStateListener() {
        appStateListenerList.clear()
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        LogCat.i("onActivityStarted")
        activityStack.add(activity)
        _activityCount.incrementAndGet()//自增
        if (!_isForeground.get()) {
            LogCat.i("onAppForeground")
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
            LogCat.i("onAppBackground")
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
            LogCat.i("onAppExit")
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

    /**
     * 判断当前进程是否是主进程
     * @param context Context
     * @return Boolean
     */
    fun isMainProcess(context: Context): Boolean {

        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val packageName = context.packageName

        val currentProcessName = getCurrentProcessName(context)

        return currentProcessName == packageName

    }


    /**
     * 获取当前进程名
     * @param context Context
     * @return String?
     */
    fun getCurrentProcessName(context: Context): String? {
        val pid = android.os.Process.myPid()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val runningAppProcesses = activityManager.runningAppProcesses
        for (processInfo in runningAppProcesses) {
            if (processInfo.pid == pid) {
                return processInfo.processName
            }
        }

        return null
    }

}