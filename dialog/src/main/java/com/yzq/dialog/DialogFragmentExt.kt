package com.yzq.dialog

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner


/**
 * @description: 自动隐藏
 * @receiver BaseDialogFragment
 * @param lifecycleOwner LifecycleOwner
 * @param dissmissEvent Array<out Lifecycle.Event>
 */
fun BaseDialogFragment.autoDissmiss(
    vararg dissmissEvent: Lifecycle.Event = arrayOf(Lifecycle.Event.ON_DESTROY)
): BaseDialogFragment {

    this.hostActivity?.lifecycle?.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

            if (event in dissmissEvent) {
                safeDismiss()
            }
        }

    })

    return this


}