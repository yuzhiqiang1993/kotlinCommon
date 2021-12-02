package com.yzq.kotlincommon

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Trace
import com.aice.appstartfaster.dispatcher.AppStartTaskDispatcher
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.StoragePath
import com.yzq.kotlincommon.task.*
import com.yzq.kotlincommon.task.test.*
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
        readMetaData()

        StoragePath.logPathInfo()

        Trace.beginSection("BaseApp_AppInit")


        AppStartTaskDispatcher
            .create()
            .setShowLog(true)
            .addAppStartTask(InitUtilsTask())
            .addAppStartTask(InitARouterTask())
            .addAppStartTask(InitBuglyTask())
            .addAppStartTask(InitLanguageTask())
            /*下面是测试的启动task*/
            .addAppStartTask(BuglyTask())
            .addAppStartTask(HeinerTask())
            .addAppStartTask(MainShortTask())
            .addAppStartTask(DuPumpTask())
            .addAppStartTask(APMTasK())
            .addAppStartTask(BPMTasK())
            .addAppStartTask(YeezyTask())
            .addAppStartTask(SmAntiTask())
            .addAppStartTask(PoizonAnalyzeTask())
            .addAppStartTask(InitTask())
            .addAppStartTask(PoizonImageTask())
            .addAppStartTask(ASynInitTask())
            .addAppStartTask(OaidTask())
            .addAppStartTask(DataCollectTask())
            .addAppStartTask(WebViewTask())
            .addAppStartTask(RestClientTask())
            .addAppStartTask(AccountTask())
            .addAppStartTask(UiUtilTask())
            .addAppStartTask(ABTestTask())
            .addAppStartTask(PushInitTask())
            .addAppStartTask(DiskCacheTask())
            .addAppStartTask(HybridInitTask())
            .addAppStartTask(DuStepServiceTask())
            .start()
            .await()

        Trace.endSection()

    }

    private fun readMetaData() {
        /*读取Manifest.xml中的 META_DATA */
        val applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        val metaData = applicationInfo.metaData
        val metaChannelValue = metaData.getString("META_CHANNEL")
        LogUtils.i("metaChannelValue=${metaChannelValue}")
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
            val setAppTracingAllowed = trace.getDeclaredMethod("setAppTracingAllowed", Boolean::class.javaPrimitiveType)
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