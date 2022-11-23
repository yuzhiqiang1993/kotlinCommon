package com.yzq.base.view_model

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils

/**
 * @description BaseViewModel
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/10/31
 * @time 10:01
 */

abstract class BaseViewModel : ViewModel(), LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        LogUtils.i("onStateChanged $source === ${event.targetState}")
    }
}
