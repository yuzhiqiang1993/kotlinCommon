package com.yzq.kotlincommon

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.*
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.yzq.common.constants.StoragePath
import com.yzq.kotlincommon.ui.activity.MainActivity
import com.yzq.lib_base.BaseApp
import com.yzq.lib_base.BuildConfig
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
        initBugly()
        /*初始化Utils*/
        initUtils()
        /*初始化ARouter*/
        initARouter()
        initLanguage()
        StoragePath.logPathInfo()

    }

    private fun readMetaData() {
        /*读取Manifest.xml中的 META_DATA */

        val applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)

        val metaData = applicationInfo.metaData
        val metaChannelValue = metaData.getString("META_CHANNEL")

        LogUtils.i("metaChannelValue=${metaChannelValue}")
    }

    private fun initLanguage() {

        /*语言*/
        val localLanguage = LanguageUtils.getSystemLanguage()
        LogUtils.i("当前系统语言:${localLanguage.language}")
        if (LanguageUtils.isAppliedLanguage()) {
            LogUtils.i("appliedLanguage语言:${LanguageUtils.getAppliedLanguage().language}")
        }

        /*如果 appliedLanguage 和 AppContextLanguage 不一致时  统一语言环境*/
        if (LanguageUtils.isAppliedLanguage() && LanguageUtils.getAppliedLanguage().language != LanguageUtils.getAppContextLanguage().language) {
            LanguageUtils.updateAppContextLanguage(
                LanguageUtils.getAppliedLanguage()
            ) {
                LogUtils.i("统一语言环境")

                LogUtils.i("getAppContextLanguage:${LanguageUtils.getAppContextLanguage().language}")
                LogUtils.i("getAppliedLanguage:${LanguageUtils.getAppliedLanguage().language}")

            }
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

        Utils.init(this)
        val config = LogUtils.getConfig()
            .setLogSwitch(BuildConfig.DEBUG)
            .setGlobalTag(AppUtils.getAppName())
            .setConsoleSwitch(BuildConfig.DEBUG)

        LogUtils.d(config.toString())

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