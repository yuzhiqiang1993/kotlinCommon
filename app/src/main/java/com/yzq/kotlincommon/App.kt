package com.yzq.kotlincommon

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Trace
import com.aice.appstartfaster.dispatcher.AppStartTaskDispatcher
import com.facebook.soloader.SoLoader
import com.jakewharton.threetenabp.AndroidThreeTen
import com.therouter.TheRouter
import com.xeon.asr_demo.ASRManager
import com.yzq.application.AppManager
import com.yzq.application.AppStateListener
import com.yzq.img.CoilManager
import com.yzq.kotlincommon.task.main_thread_task.InitAPMTask
import com.yzq.kotlincommon.task.main_thread_task.InitAliPushTask
import com.yzq.kotlincommon.task.main_thread_task.InitCrashReportTask
import com.yzq.kotlincommon.task.main_thread_task.InitLocationTask
import com.yzq.kotlincommon.task.main_thread_task.InitMMKVTask
import com.yzq.kotlincommon.task.main_thread_task.InitSmartRefreshTask
import com.yzq.kotlincommon.task.main_thread_task.InitStateLayoutConfigTask
import com.yzq.kotlincommon.task.main_thread_task.InitTlogTask
import com.yzq.kotlincommon.task.work_thread_task.InitUtilsTask
import com.yzq.logger.Logger


/**
 * @description: Application基类
 * @author : yzq
 * @date : 2019/3/18
 * @time : 11:28
 *
 */


class App : Application(), AppStateListener {

    override fun onCreate() {
        super.onCreate()
        AppManager.init(this, BuildConfig.DEBUG)
        Logger.setDebug(BuildConfig.DEBUG)

        if (AppManager.isMainProcess()) {
            Logger.i("主进程")
            //初始化RN SoLoader
            SoLoader.init(this, false)

            /*监听App是否退出*/
            AppManager.addAppStateListener(this)
            /*读清单配置文件里的数据*/
            readMetaData()
            Trace.beginSection("BaseApp_AppInit")
            /*日期库初始化*/
            AndroidThreeTen.init(this)
            CoilManager.init()
            AppStartTaskDispatcher
                .create()
                .setShowLog(true)
                .addAppStartTask(InitCrashReportTask())
                .addAppStartTask(InitUtilsTask())
                .addAppStartTask(InitMMKVTask())
                .addAppStartTask(InitStateLayoutConfigTask())
                .addAppStartTask(InitSmartRefreshTask())
                .addAppStartTask(InitAPMTask())
                .addAppStartTask(InitTlogTask())
                .addAppStartTask(InitAliPushTask())
                .addAppStartTask(InitLocationTask())
                .start()
                .await()

            ASRManager.init(this)

            Trace.endSection()
        } else {
            Logger.i("非主进程")

            if (AppManager.getCurrentProcessName().equals("${packageName}:channel")) {

                Logger.i("channel进程,初始化推送")
                /*channel进程也要对推送初始化 https://help.aliyun.com/document_detail/434662.html?spm=a2c4g.11186623.0.0.72aa5b78qNHbvx*/
                AppStartTaskDispatcher
                    .create()
                    .setShowLog(true)
                    .addAppStartTask(InitAliPushTask())
                    .start()
                    .await()
            }

        }
    }


    private fun readMetaData() {


        Logger.i("Build.VERSION.SDK_INT = ${Build.VERSION.SDK_INT}")

        val supportedAbis = Build.SUPPORTED_ABIS
        Logger.i("支持的指令集:${supportedAbis.contentToString()}")

        /*读取Manifest.xml中的 META_DATA */
        val applicationInfo =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getApplicationInfo(
                    packageName,
                    PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong())
                )
            } else {
                packageManager.getApplicationInfo(
                    packageName,
                    PackageManager.GET_META_DATA
                )
            }

        val metaData = applicationInfo.metaData
        val metaChannelValue = metaData.getString("META_CHANNEL")
        Logger.i("metaChannelValue=$metaChannelValue")

        /*读取BuildConfig中的变量*/
        Logger.i("BuildConfig.BASE_URL = ${BuildConfig.BASE_URL}")
        Logger.i("BuildConfig.LOG_DEBUG = ${BuildConfig.LOG_DEBUG}")
        Logger.i("BuildConfig.DEBUG = ${BuildConfig.DEBUG}")
        Logger.i("BuildConfig.FLAVOR = ${BuildConfig.FLAVOR}")
        Logger.i("BuildConfig.FLAVOR_ENV = ${BuildConfig.FLAVOR_ENV}")
        Logger.i("BuildConfig.FLAVOR_CHANNEL = ${BuildConfig.FLAVOR_CHANNEL}")
        Logger.i("BuildConfig.BUILD_TYPE = ${BuildConfig.BUILD_TYPE}")
        Logger.i("BuildConfig.VERSION_CODE = ${BuildConfig.VERSION_CODE}")
        Logger.i("BuildConfig.VERSION_NAME = ${BuildConfig.VERSION_NAME}")
        Logger.i("BuildConfig.APPLICATION_ID = ${BuildConfig.APPLICATION_ID}")

        val httpUserAgent = System.getProperty("http.agent")
        Logger.i("httpUserAgent:$httpUserAgent")
    }

    override fun attachBaseContext(base: Context?) {
        allowSysTraceInDebug()
        TheRouter.isDebug = BuildConfig.DEBUG
        super.attachBaseContext(base)
    }

    /**
     * 正式包这个方法会通过 bytex  method_call_opt 插件移除,
     * 配置在 app/plugins/bytex.gradle 中
     */
    @SuppressLint("DiscouragedPrivateApi")
    private fun allowSysTraceInDebug() {
        runCatching {
            val trace = Class.forName("android.os.Trace")
            val setAppTracingAllowed =
                trace.getDeclaredMethod("setAppTracingAllowed", Boolean::class.javaPrimitiveType)
            setAppTracingAllowed.invoke(null, BuildConfig.DEBUG)//debug才抓trace
        }
    }

    override fun onAppExit() {
        /*应用退出了*/
        Logger.i("App退出了")
    }
}
