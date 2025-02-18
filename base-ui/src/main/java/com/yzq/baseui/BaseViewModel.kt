package com.yzq.baseui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

/**
 * @description BaseViewModel
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

abstract class BaseViewModel : ViewModel(), LifecycleEventObserver {

    protected val TAG = "${this.javaClass.simpleName}-${this.hashCode()}"

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

    }
}
