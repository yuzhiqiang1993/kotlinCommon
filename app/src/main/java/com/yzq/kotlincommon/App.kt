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
import com.tencent.bugly.library.Bugly
import com.tencent.bugly.library.BuglyBuilder
import com.tencent.feedback.eup.CrashHandleListener
import com.therouter.TheRouter
import com.xeon.asr_demo.ASRManager
import com.yzq.application.AppManager
import com.yzq.application.AppStateListener
import com.yzq.application.getCurrentProcessName
import com.yzq.application.isMainProcess
import com.yzq.img.CoilManager
import com.yzq.kotlincommon.task.main_thread_task.InitAPMTask
import com.yzq.kotlincommon.task.main_thread_task.InitAliPushTask
import com.yzq.kotlincommon.task.main_thread_task.InitCrashReportTask
import com.yzq.kotlincommon.task.main_thread_task.InitLocationTask
import com.yzq.kotlincommon.task.main_thread_task.InitMMKVTask
import com.yzq.kotlincommon.task.main_thread_task.InitSmartRefreshTask
import com.yzq.kotlincommon.task.main_thread_task.InitStateLayoutConfigTask
import com.yzq.kotlincommon.task.main_thread_task.InitTlogTask
import com.yzq.kotlincommon.task.work_thread_task.InitFileOperatorTask
import com.yzq.kotlincommon.task.work_thread_task.InitToasterTask
import com.yzq.logger.Logger
import com.yzq.logger.console.ConsoleLogConfig
import com.yzq.logger.console.ConsoleLogPrinter
import com.yzq.logger.file.FileLogConfig
import com.yzq.logger.file.FileLogPrinter
import com.yzq.logger.view.core.ViewLogConfig
import com.yzq.logger.view.core.ViewLogPrinter


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


        if (AppManager.isMainProcess()) {
            Logger.i("主进程")
            //初始化RN SoLoader
            SoLoader.init(this, false)

            /*监听App是否退出*/
            AppManager.addAppStateListener(this)

            initBugly()


            //读清单配置文件里的数据
            readMetaData()
            Trace.beginSection("BaseApp_AppInit")
            //日期库初始化
            AndroidThreeTen.init(this)
            CoilManager.init()
            AppStartTaskDispatcher.create().setShowLog(true).addAppStartTask(InitCrashReportTask())
                .addAppStartTask(InitMMKVTask()).addAppStartTask(InitStateLayoutConfigTask())
                .addAppStartTask(InitSmartRefreshTask()).addAppStartTask(InitAPMTask())
                .addAppStartTask(InitTlogTask()).addAppStartTask(InitAliPushTask())
                .addAppStartTask(InitLocationTask()).addAppStartTask(InitToasterTask())
                .addAppStartTask(InitFileOperatorTask()).start().await()

            ASRManager.init(this)

            Trace.endSection()
        } else {
            Logger.i("非主进程")

            if (AppManager.getCurrentProcessName().equals("${packageName}:channel")) {

                Logger.i("channel进程,初始化推送")/*channel进程也要对推送初始化 https://help.aliyun.com/document_detail/434662.html?spm=a2c4g.11186623.0.0.72aa5b78qNHbvx*/
                AppStartTaskDispatcher.create().setShowLog(true).addAppStartTask(InitAliPushTask())
                    .start().await()
            }

        }
    }

    private fun initBugly() {
        val buglyBuilder = BuglyBuilder("appkey", "appId")

        buglyBuilder.setCrashHandleListener(object : CrashHandleListener {
            /**
             * Crash处理回调时，此接口返回的数据以附件的形式上报，附件名userExtraByteData
             * @param isNativeCrashed 是否Native异常
             * @param crashType 异常的类型
             * @param crashStack 异常堆栈
             * @param nativeSiCode native异常时的SI_CODE，非Native异常此数据无效
             * @param crashTime native异常的发生时间
             * @return 上报的附件字节流
             */
            override fun getCrashExtraData(
                isNativeCrashed: Boolean,
                crashType: String?,
                crashAddress: String?,
                crashStack: String?,
                nativeSiCode: Int,
                crashTime: Long
            ): ByteArray {
                Logger.e(
                    "getCrashExtraData:",
                    "isNativeCrashed:$isNativeCrashed,crashType:$crashType,crashAddress:$crashAddress,crashStack:$crashStack,nativeSiCode:$nativeSiCode,crashTime:$crashTime"
                )
                return byteArrayOf()
            }


            /**
             * Crash处理回调时，此接口返回的数据在附件extraMessage.txt中展示
             * @param isNativeCrashed 是否Native异常
             * @param crashType 异常的类型
             * @param crashStack 异常堆栈
             * @param nativeSiCode native异常时的SI_CODE，非Native异常此数据无效
             * @param crashTime native异常的发生时间
             * @return 上报的数据
             */
            override fun getCrashExtraMessage(
                isNativeCrashed: Boolean,
                crashType: String?,
                crashAddress: String?,
                crashStack: String?,
                nativeSiCode: Int,
                crashTime: Long
            ): String {
                Logger.e(
                    "getCrashExtraMessage:",
                    "isNativeCrashed:$isNativeCrashed,crashType:$crashType,crashAddress:$crashAddress,crashStack:$crashStack,nativeSiCode:$nativeSiCode,crashTime:$crashTime"
                )

                return ""
            }

            /**
             * Crash处理回调前，执行此接口
             * @param isNativeCrashed 是否Native异常
             */
            override fun onCrashHandleStart(isNativeCrashed: Boolean) {
                Logger.e("onCrashHandleStart:isNativeCrashed:$isNativeCrashed")
            }

            override fun onCrashHandleEnd(p0: Boolean): Boolean {

                return true
            }


            override fun onCrashSaving(
                p0: Boolean,
                p1: String?,
                p2: String?,
                p3: String?,
                p4: String?,
                p5: Int,
                p6: Long,
                p7: String?,
                p8: String?,
                p9: String?,
                p10: String?
            ): Boolean {
                return true
            }

        })

        Bugly.init(this, buglyBuilder)

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
