package com.yzq.common

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import java.util.*


/**
 * @description: Application入口
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
        /*初始化Utils*/
        initUtils()

        /*初始化Crash*/
        //initCrash()

        /*初始化ARouter*/
        initARouter()

        registerActivityLifecycleCallbacks(this)
        /*打印路径信息*/
        //StoragePath.getPathInfo()
    }

    private fun initCrash() {

        CrashUtils.OnCrashListener { crashInfo, _ ->

            LogUtils.e("Crash！！！${crashInfo}")

            LogUtils.i("重新启动app")
            AppUtils.relaunchApp()

        }
    }


    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog() // 开启日志
            ARouter.openDebug() // 使用InstantRun的时候，需要打开该开关，上线之后关闭，否则有安全风险
            ARouter.printStackTrace() // 打印日志的时候打印线程堆栈
        }

        ARouter.init(this)


    }

    /**
     * 初始化util工具类
     *
     */

    private fun initUtils() {


        Utils.init(AppContext)
        val config = LogUtils.getConfig()
                .setLogSwitch(BuildConfig.DEBUG)
                .setGlobalTag(AppUtils.getAppName())
                .setConsoleSwitch(BuildConfig.DEBUG)

        LogUtils.d(config.toString())

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