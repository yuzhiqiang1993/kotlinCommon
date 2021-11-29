package com.yzq.kotlincommon.task

import com.aice.appstartfaster.task.AppStartTask
import com.blankj.utilcode.util.LanguageUtils
import com.blankj.utilcode.util.LogUtils
import java.util.*

class InitLanguageTask : AppStartTask() {
    override fun run() {

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

    override fun needWait() = true

    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {
        val dependsTaskList: MutableList<Class<out AppStartTask?>> = ArrayList()
        dependsTaskList.add(InitUtilsTask::class.java)

        return dependsTaskList
    }

    override fun isRunOnMainThread() = true
}