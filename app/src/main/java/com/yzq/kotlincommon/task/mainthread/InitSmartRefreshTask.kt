package com.yzq.kotlincommon.task.mainthread

import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.yzq.application.AppContext
import com.yzq.appstartup.MainThreadTask

/**
 * @description 下拉刷新，加载更多
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/18
 * @time 11:36
 */

class InitSmartRefreshTask : MainThreadTask() {
    override fun taskRun() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            MaterialHeader(AppContext)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(AppContext)
        }
    }
}
