package com.yzq.kotlincommon

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Trace
import com.aice.appstartfaster.dispatcher.AppStartTaskDispatcher
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ProcessUtils
import com.tencent.mmkv.MMKV
import com.yzq.common.constants.StoragePath
import com.yzq.kotlincommon.task.main_thread_task.*
import com.yzq.kotlincommon.task.work_thread_task.InitARouterTask
import com.yzq.kotlincommon.task.work_thread_task.InitUtilsTask
import com.yzq.lib_base.BaseApp
import java.lang.reflect.InvocationTargetException

/**
 * @description: Application基类
 * @author : yzq
 * @date   : 2019/3/18
 * @time   : 11:28
 *
 */

class App : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        LogUtils.e("ProcessUtils.getCurrentProcessName() = ${ProcessUtils.getCurrentProcessName()}")

        if (ProcessUtils.isMainProcess()) {
            LogUtils.i("主进程")
            readMetaData()

            StoragePath.logPathInfo()

            Trace.beginSection("BaseApp_AppInit")

            MMKV.initialize(this)

            AppStartTaskDispatcher
                .create()
                .setShowLog(true)
                .addAppStartTask(InitARouterTask())
                .addAppStartTask(InitUtilsTask())
                .addAppStartTask(InitAPMTask())
                .addAppStartTask(InitTlogTask())
                .addAppStartTask(InitCrashReportTask())
                .addAppStartTask(InitAliPushTask())
                .addAppStartTask(InitMobileAnalyticsTask())
                .start()
                .await()

            Trace.endSection()
        } else {
            LogUtils.i("非主进程")
        }


    }

    private fun readMetaData() {

        LogUtils.i("Build.VERSION.SDK_INT = ${Build.VERSION.SDK_INT}")

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
        LogUtils.i("metaChannelValue=${metaChannelValue}")

        /*读取BuildConfig中的变量*/
        LogUtils.i("BuildConfig.BASE_URL = ${BuildConfig.BASE_URL}")
        LogUtils.i("BuildConfig.LOG_DEBUG = ${BuildConfig.LOG_DEBUG}")
    }

    override fun attachBaseContext(base: Context?) {
        allowSysTraceInDebug()
        super.attachBaseContext(base)
    }

    /**
     * 正式包这个方法会通过 bytex  method_call_opt 插件移除,
     * 配置在 app/plugins/bytex.gradle 中
     */
    @SuppressLint("DiscouragedPrivateApi")
    private fun allowSysTraceInDebug() {
        try {
            val trace = Class.forName("android.os.Trace")
            val setAppTracingAllowed =
                trace.getDeclaredMethod("setAppTracingAllowed", Boolean::class.javaPrimitiveType)
            setAppTracingAllowed.invoke(null, true)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
    }

}