package com.yzq.router

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import com.therouter.TheRouter
import com.therouter.router.Navigator
import com.therouter.router.interceptor.NavigationCallback


/**
 * @description: 跳转完成后直接关闭当前Activity
 * @author : yzq
 * @date : 2019/2/22
 * @time : 16:00
 *
 */


fun Navigator.navFinish(activity: ComponentActivity) {
    navigation(activity, object : NavigationCallback() {
        override fun onArrival(navigator: Navigator) {
            super.onArrival(navigator)
            if (!activity.isDestroyed) {
                activity.finishAfterTransition()
            }

        }
    })

}


/**
 * @description: 清除之前所有页面并跳转到新页面
 * @author : yzq
 * @date : 2019/2/22
 * @time : 16:21
 *
 */

fun Navigator.navClear(activity: ComponentActivity) {
    withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        .navigation(activity)
}

fun ComponentActivity.nav(path: String) {
    TheRouter.build(path)
        .navigation(this)
}

fun ComponentActivity.navFinish(path: String) {
    TheRouter.build(path)
        .navFinish(this)
}

fun ComponentActivity.navClear(path: String) {
    TheRouter.build(path)
        .navClear(this)
}

fun Fragment.nav(path: String) {
    TheRouter.build(path).navigation(requireActivity())
}

fun Fragment.navFinish(path: String) {
    TheRouter.build(path)
        .navFinish(requireActivity())
}

fun Fragment.navClear(path: String) {
    TheRouter.build(path)
        .navClear(requireActivity())
}
