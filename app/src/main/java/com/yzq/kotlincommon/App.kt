package com.yzq.kotlincommon

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Trace
import com.aice.appstartfaster.dispatcher.AppStartTaskDispatcher
import com.facebook.soloader.SoLoader
import com.therouter.TheRouter
import com.yzq.aliemas.inittask.InitAliPushTask
import com.yzq.aliemas.inittask.InitCrashReportTask
import com.yzq.application.AppManager
import com.yzq.application.AppStateListener
import com.yzq.application.getCurrentProcessName
import com.yzq.application.isMainProcess
import com.yzq.kotlincommon.task.mainthread.InitAsrBaiduTask
import com.yzq.kotlincommon.task.mainthread.InitAsrIflyTask
import com.yzq.kotlincommon.task.mainthread.InitCoilTask
import com.yzq.kotlincommon.task.mainthread.InitLocationTask
import com.yzq.kotlincommon.task.mainthread.InitMMKVTask
import com.yzq.kotlincommon.task.mainthread.InitSmartRefreshTask
import com.yzq.kotlincommon.task.mainthread.InitStateLayoutConfigTask
import com.yzq.kotlincommon.task.mainthread.InitThreeTenTask
import com.yzq.kotlincommon.task.workthread.InitFileOperatorTask
import com.yzq.kotlincommon.task.workthread.InitToasterTask
import com.yzq.logger.Logger
import com.yzq.logger.console.ConsoleLogConfig
import com.yzq.logger.console.ConsoleLogPrinter
import com.yzq.logger.file.FileLogConfig
import com.yzq.logger.file.FileLogPrinter
import com.yzq.logger.view.core.ViewLogConfig
import com.yzq.logger.view.core.ViewLogPrinter
import com.yzq.util.ext.getDeviceInfo


/**
 * @description: Application基类
 * @author : yzq
 * @date : 2019/3/18
 * @time : 11:28
 *
 */
class App : Application(), AppStateListener {

    companion object {
        const val TAG = "App"
    }

    override fun onCreate() {
        super.onCreate()
        AppManager.init(this, BuildConfig.DEBUG)

        if (BuildConfig.DEBUG) {
            Logger.addPrinter(
                ConsoleLogPrinter.getInstance(
                    ConsoleLogConfig.Builder().enable(BuildConfig.DEBUG).build()
                )
            ).addPrinter(
                FileLogPrinter.getInstance(
                    FileLogConfig.Builder().enable(BuildConfig.DEBUG).writeLogInterval(60)
                        .storageDuration(1 * 24).build()

                )
            ).addPrinter(
                ViewLogPrinter.getInstance(
                    ViewLogConfig.Builder().enable(BuildConfig.DEBUG).build()
                )
            ).debug(true)
        }

        val deviceInfo = getDeviceInfo()
        Logger.it(TAG, "$deviceInfo")

        if (AppManager.isMainProcess()) {
            Logger.i("主进程")
            //初始化RN SoLoader
            SoLoader.init(this, false)

            /*监听App是否退出*/
            AppManager.addAppStateListener(this)

            //读清单配置文件里的数据
            readMetaData()
            Trace.beginSection("BaseApp_AppInit")


            AppStartTaskDispatcher
                .create()
                .setShowLog(true)
                .addAppStartTask(InitCoilTask())
                .addAppStartTask(InitThreeTenTask())
                .addAppStartTask(InitCrashReportTask())
                .addAppStartTask(InitMMKVTask())
                .addAppStartTask(InitStateLayoutConfigTask())
                .addAppStartTask(InitSmartRefreshTask())
//                .addAppStartTask(InitAPMTask())
//                .addAppStartTask(InitTlogTask())
//                .addAppStartTask(InitAliPushTask())
                .addAppStartTask(InitLocationTask())
                .addAppStartTask(InitToasterTask())
                .addAppStartTask(InitFileOperatorTask())
                .addAppStartTask(InitAsrIflyTask())
                .addAppStartTask(InitAsrBaiduTask())
                .start()
                .await()



            Trace.endSection()
        } else {
            Logger.i("非主进程")

            if (AppManager.getCurrentProcessName().equals("${packageName}:channel")) {
                Logger.i("channel进程,初始化推送")
                //channel进程也要对推送初始化 https://help.aliyun.com/document_detail/434662.html?spm=a2c4g.11186623.0.0.72aa5b78qNHbvx
                AppStartTaskDispatcher.create().setShowLog(true).addAppStartTask(InitAliPushTask())
                    .start().await()
            }

        }

    }


    private fun readMetaData() {

        Logger.i("Build.VERSION.SDK_INT = ${Build.VERSION.SDK_INT}")

        val supportedAbis = Build.SUPPORTED_ABIS
        Logger.i("支持的指令集:${supportedAbis.contentToString()}")

        //读取Manifest.xml中的 META_DATA
        val applicationInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong())
            )
        } else {
            packageManager.getApplicationInfo(
                packageName, PackageManager.GET_META_DATA
            )
        }

        val metaData = applicationInfo.metaData
        val metaChannelValue = metaData.getString("META_CHANNEL")
        Logger.i("metaChannelValue=$metaChannelValue")

        //读取BuildConfig中的变量
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
        //应用退出了
        Logger.i("App退出了")
    }
}
