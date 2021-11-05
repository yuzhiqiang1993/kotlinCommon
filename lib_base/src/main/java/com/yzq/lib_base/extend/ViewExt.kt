package com.yzq.lib_base.extend

import android.view.View
import kotlinx.coroutines.delay

/**
 * 点击防抖
 * @receiver View
 * @param time Long
 * @param block Function0<Unit>
 */
inline fun View.setOnThrottleTimeClick(
    time: Long = 300,
    crossinline block: () -> Unit
) {
    setOnClickListener {
        isClickable = false
        block()
        postDelayed({ isClickable = true }, time)
    }
}
