package com.yzq.kotlincommon

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.SUPPORTED_ABIS
import android.os.Trace
import com.aice.appstartfaster.dispatcher.AppStartTaskDispatcher
import com.blankj.utilcode.util.ArrayUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ProcessUtils
import com.tencent.mmkv.MMKV
import com.therouter.TheRouter
import com.yzq.application.AppManager
import com.yzq.application.AppStateListener
import com.yzq.application.BaseApp
import com.yzq.kotlincommon.task.main_thread_task.*
import com.yzq.kotlincommon.task.work_thread_task.InitUtilsTask


/**
 * @description: Application基类
 * @author : yzq
 * @date : 2019/3/18
 * @time : 11:28
 *
 */

class App : BaseApp(), AppStateListener {

    override fun onCreate() {
        super.onCreate()
        LogUtils.e("ProcessUtils.getCurrentProcessName() = ${ProcessUtils.getCurrentProcessName()}")
        LogUtils.i("packageName:${packageName}")

        if (ProcessUtils.isMainProcess()) {
            LogUtils.i("主进程")
            /*监听App是否退出*/
            AppManager.addAppStateListener(this)

            /*读清单配置文件里的数据*/
            readMetaData()


            Trace.beginSection("BaseApp_AppInit")

            MMKV.initialize(this)

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
                .start()
                .await()



            Trace.endSection()
        } else {
            LogUtils.i("非主进程")

            if (ProcessUtils.getCurrentProcessName()
                    .equals("${packageName}:channel")
            ) {

                LogUtils.i("channel进程,初始化推送")
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

        LogUtils.i("Build.VERSION.SDK_INT = ${Build.VERSION.SDK_INT}")

        val supportedAbis = SUPPORTED_ABIS
        LogUtils.i("支持的指令集:${ArrayUtils.toString(supportedAbis)}")

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
        LogUtils.i("metaChannelValue=$metaChannelValue")

        /*读取BuildConfig中的变量*/
        LogUtils.i("BuildConfig.BASE_URL = ${BuildConfig.BASE_URL}")
        LogUtils.i("BuildConfig.LOG_DEBUG = ${BuildConfig.LOG_DEBUG}")
        LogUtils.i("BuildConfig.DEBUG = ${BuildConfig.DEBUG}")
        LogUtils.i("BuildConfig.FLAVOR = ${BuildConfig.FLAVOR}")
        LogUtils.i("BuildConfig.FLAVOR_ENV = ${BuildConfig.FLAVOR_ENV}")
        LogUtils.i("BuildConfig.FLAVOR_CHANNEL = ${BuildConfig.FLAVOR_CHANNEL}")
        LogUtils.i("BuildConfig.BUILD_TYPE = ${BuildConfig.BUILD_TYPE}")
        LogUtils.i("BuildConfig.VERSION_CODE = ${BuildConfig.VERSION_CODE}")
        LogUtils.i("BuildConfig.VERSION_NAME = ${BuildConfig.VERSION_NAME}")
        LogUtils.i("BuildConfig.APPLICATION_ID = ${BuildConfig.APPLICATION_ID}")

        val httpUserAgent = System.getProperty("http.agent")
        LogUtils.i("httpUserAgent:$httpUserAgent")
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
            setAppTracingAllowed.invoke(null, false)//debug才抓trace
        }
    }

    override fun onAppExit() {
        /*应用退出了*/
        LogUtils.i("App退出了")
    }
}
