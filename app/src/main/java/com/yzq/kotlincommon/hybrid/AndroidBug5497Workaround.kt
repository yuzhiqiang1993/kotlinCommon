package com.yzq.kotlincommon.hybrid

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.widget.FrameLayout

/**
 * @description: 网上找的方案  https://github.com/Mightted/AndroidBug5497WorkaroundKt
 *使用方式如下
 * override fun onWindowFocusChanged(hasFocus: Boolean) {
 *      super.onWindowFocusChanged(hasFocus)
 *      val height = resources.displayMetrics.heightPixels //屏幕高度
 *      AndroidBug5497Workaround.assistActivity(this,height)
 *}
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/6/24
 * @time   : 15:22
 */

class AndroidBug5497Workaround(activity: Activity, height: Int, hideBar: Boolean) {

    companion object {
        /**
         * height:通过外部传入，初始界面高度，用于改变界面后恢复原样，建议在activity的onWindowFocusChanged中初始化
         * hideBar:是否隐藏状态栏，用于这个是用户可以控制的，所以采用外部传入
         */
        fun assistActivity(activity: Activity, height: Int, hideBar: Boolean = false) {
            AndroidBug5497Workaround(activity, height, hideBar)
        }
    }

    private val mChildOfContent: View
    private var usableHeightPrevious: Int = 0
    private val frameLayoutParams: FrameLayout.LayoutParams
    private val contentHeight: Int
    private val statusBarHeight: Int

    init {
        val content: FrameLayout = activity.findViewById(android.R.id.content)
        contentHeight = height
        mChildOfContent = content.getChildAt(0)
        statusBarHeight = if (hideBar) getStatusBarHeight(activity) else 0

        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener {
            possiblyResizeChildOfContent()
        }
        frameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }

    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                frameLayoutParams.height =
                    usableHeightSansKeyboard - heightDifference + statusBarHeight
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = contentHeight + statusBarHeight
            }
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return (r.bottom - r.top)
    }

    private fun getStatusBarHeight(activity: Activity): Int {

        return activity.resources.run {
            //获取status_bar_height资源的ID
            val resourceId = getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
            )
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                getDimensionPixelSize(resourceId)
            } else {
                0
            }
        }
    }
}