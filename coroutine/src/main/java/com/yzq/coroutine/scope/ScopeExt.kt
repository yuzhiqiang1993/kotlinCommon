package com.yzq.coroutine.scope

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


/**
 * Scope life
 * 具备生命周期感知能力的作用域  生命周期跟随 [LifecycleOwner]
 * @param lifeEvent 执行取消逻辑的生命周期事件，默认 [Lifecycle.Event.ON_DESTROY]
 * @param dispatcher 调度器，默认 [Dispatchers.Main]
 * @param block  要执行的代码块
 * @receiver [LifecycleOwner]
 */
fun LifecycleOwner.lifeScope(
    lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> Unit
) = AndroidScope(this, lifeEvent, dispatcher).launch(block)
