package com.yzq.common

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import java.util.*

open class BaseApp : Application(), Application.ActivityLifecycleCallbacks {


    private val activityStack: Stack<Activity> = Stack()


    /*全局伴生对象*/
    companion object {
        lateinit var instance: BaseApp
    }

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    }


    override fun onCreate() {
        super.onCreate()
        instance = this


        /*初始化Utils*/
        initUtils()

        /*初始化ARouter*/
        initARouter()

        registerActivityLifecycleCallbacks(this)


    }


    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog() // 开启日志
            ARouter.openDebug() // 使用InstantRun的时候，需要打开该开关，上线之后关闭，否则有安全风险
            ARouter.printStackTrace() // 打印日志的时候打印线程堆栈
        }

        ARouter.init(this)


    }

    private fun initUtils() {
        Utils.init(this)

        val config = LogUtils.getConfig()
                .setLogSwitch(BuildConfig.DEBUG)
                .setGlobalTag(AppUtils.getAppName())
                .setConsoleSwitch(BuildConfig.DEBUG)

        LogUtils.d(config.toString())

    }


    fun exitApp() {
        for (activity in activityStack) {
            activity.finish()
        }
    }


    override fun onActivityCreated(activity: Activity?, p1: Bundle?) {
        activityStack.add(activity)
    }

    override fun onActivityPaused(p0: Activity?) {

    }

    override fun onActivityResumed(p0: Activity?) {

    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {


    }

    override fun onActivityStarted(p0: Activity?) {


    }

    override fun onActivityStopped(p0: Activity?) {


    }

    override fun onActivityDestroyed(activity: Activity?) {

        if (activityStack.contains(activity)) {
            activityStack.remove(activity)
        }

    }


}