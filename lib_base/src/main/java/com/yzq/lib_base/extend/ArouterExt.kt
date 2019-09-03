package com.yzq.lib_base.extend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback


/**
 * @description: 跳转完成后直接关闭当前Activity
 * @author : yzq
 * @date   : 2019/2/22
 * @time   : 16:00
 *
 */

fun Postcard.navFinish(activity: AppCompatActivity) {
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

fun Postcard.navClear(activity: AppCompatActivity) {
    withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        .navigation(activity)
}