package com.yzq.kotlincommon.task.main_thread_task

import com.yzq.application.AppManager
import com.yzq.base.startup.base.MainThreadTask
import com.yzq.location_manager.LocationManager
import com.yzq.location_protocol.constant.LocationPlatform

/**
 * @description: 初始化阿里推送
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2022/1/9
 * @time : 2:57 下午
 */

class InitLocationTask : MainThreadTask() {

    private val TAG = javaClass.canonicalName
    override fun taskRun() {
        /*https://help.aliyun.com/document_detail/195006.html*/

        LocationManager.init(
            AppManager.application,
            true,
            arrayOf(LocationPlatform.GAO_DE),
            LocationPlatform.GAO_DE
        )


    }
}
