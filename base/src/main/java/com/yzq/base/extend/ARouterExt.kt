package com.yzq.base.extend

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @description: 跳转完成后直接关闭当前Activity
 * @author : yzq
 * @date : 2019/2/22
 * @time : 16:00
 *
 */

fun Postcard.navFinish(activity: FragmentActivity) {
    navigation(
        activity,
        object : NavCallback() {
            override fun onArrival(postcard: Postcard?) {
                activity.finish()
            }
        }
    )
}

/**
 * @description: 清除之前所有页面并跳转到新页面
 * @author : yzq
 * @date : 2019/2/22
 * @time : 16:21
 *
 */

fun Postcard.navClear(activity: FragmentActivity) {
    withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        .navigation(activity)
}

fun FragmentActivity.nav(path: String) {
    ARouter.getInstance().build(path)
        .navigation(this)
}

fun FragmentActivity.navFinish(path: String) {
    ARouter.getInstance().build(path)
        .navFinish(this)
}

fun FragmentActivity.navClear(path: String) {
    ARouter.getInstance().build(path)
        .navClear(this)
}

fun Fragment.nav(path: String) {
    ARouter.getInstance().build(path).navigation(requireActivity())
}

fun Fragment.navFinish(path: String) {
    ARouter.getInstance().build(path)
        .navFinish(requireActivity())
}

fun Fragment.navClear(path: String) {
    ARouter.getInstance().build(path)
        .navClear(requireActivity())
}
