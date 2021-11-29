package com.yzq.kotlincommon

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Trace
import com.blankj.utilcode.util.LogUtils
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.yzq.common.constants.StoragePath
import com.yzq.kotlincommon.ui.activity.MainActivity
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
        LogUtils.i("onCreate")
        Trace.beginSection("BaseAppInit")
        super.onCreate()

        /*读取Manifest.xml中的 META_DATA */

        val applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)

        val metaData = applicationInfo.metaData
        val metaChannelValue = metaData.getString("META_CHANNEL")

        LogUtils.i("metaChannelValue=${metaChannelValue}")


        Trace.beginSection("initBugly")
        initBugly()
        Trace.endSection()

        StoragePath.logPathInfo()
        Trace.endSection()

    }

    /**
     * 初始化bugly
     *
     */
    private fun initBugly() {
        /*初始化Bugly*/
        Beta.showInterruptedStrategy = true
        Beta.canShowUpgradeActs.add(MainActivity::class.java)
        /*第三个参数表示是否在debug下也上报日志信息*/
        Bugly.init(this, "52e655831e", false)

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