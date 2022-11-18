package com.yzq.coroutine.scope

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * @description 针对view的具备生命周期检测以及异常兜底的协程作用域
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/18
 * @time 17:29
 */

class ViewLifeSafetyScope(view: View, dispatcher: CoroutineDispatcher = Dispatchers.Main) :
    LifeSafetyScope(dispatcher = dispatcher) {
    init {
        ViewTreeLifecycleOwner.get(view)?.lifecycle?.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    close()
                }
            }
        })
    }
}
