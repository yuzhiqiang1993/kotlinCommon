package com.yzq.common.extend

import android.content.Intent
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.yzq.common.ui.BaseActivity


/**
 * @description: 跳转完成后直接关闭当前Activity
 * @author : yzq
 * @date   : 2019/2/22
 * @time   : 16:00
 *
 */

fun Postcard.navFinish(activity: BaseActivity) {
    navigation(activity, object : NavCallback() {
        override fun onArrival(postcard: Postcard?) {
            activity.finish()
        }
    })
}


/**
 * @description: 清除之前所有页面并跳转到新页面
 * @author : yzq
 * @date   : 2019/2/22
 * @time   : 16:21
 *
 */

fun Postcard.navClear(activity: BaseActivity) {
    withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation(activity)
}