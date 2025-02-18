package com.yzq.core.extend

import android.view.View

/**
 * 点击防抖
 * @receiver View
 * @param time Long
 * @param block Function0<Unit>
 */
inline fun View.setOnThrottleTimeClick(
    time: Long = 300,
    crossinline block: () -> Unit,
) {
    setOnClickListener {
        isClickable = false
        block()
        //指定时间后将控件置为可点击
        postDelayed({ isClickable = true }, time)
    }
}
