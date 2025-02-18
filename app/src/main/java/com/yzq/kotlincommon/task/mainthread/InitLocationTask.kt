package com.yzq.kotlincommon.task.mainthread

import com.yzq.appstartup.MainThreadTask
import com.yzq.location_manager.LocationManager
import com.yzq.location_protocol.constant.LocationPlatform

/**
 * @description: 初始化定位
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2022/1/9
 * @time : 2:57 下午
 */

class InitLocationTask : MainThreadTask() {

    private val TAG = javaClass.canonicalName
    override fun taskRun() {

        LocationManager.init(
            true,
            arrayOf(LocationPlatform.GAO_DE),
            LocationPlatform.GAO_DE
        )


    }
}
