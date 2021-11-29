package com.yzq.lib_base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import java.util.*

/**
 * @description: BaseApplication
 * @author : yzq
 * @date   : 2019/3/18
 * @time   : 11:29
 *
 */

open class BaseApp : Application(), Application.ActivityLifecycleCallbacks {

    private val activityStack: Stack<Activity> = Stack()

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    companion object {
        lateinit var INSTANCE: BaseApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        registerActivityLifecycleCallbacks(this)
    }

    /**
     * 退出App
     *
     */
    fun exitApp() {
        activityStack.forEach {
            it.finish()

        }
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        activityStack.add(activity)
    }

    override fun onActivityStarted(p0: Activity) {

    }

    override fun onActivityResumed(p0: Activity) {
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activityStack.contains(activity)) {
            activityStack.remove(activity)
        }
    }

}