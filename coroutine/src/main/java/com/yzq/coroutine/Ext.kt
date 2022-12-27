package com.yzq.coroutine.scope

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.yzq.coroutine.SafetyCoroutine
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * 有异常兜底功能的 launch
 *
 * @param T
 * @param context
 * @param start
 * @param block
 * @receiver
 * @return
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <T> CoroutineScope.launchSafety(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T,
): SafetyCoroutine<T> {
    val newContext = newCoroutineContext(context)
    val coroutine = SafetyCoroutine<T>(newContext)
    /**
     * 这里有个问题是如果block代码块中立刻抛出异常的话，由于catch还没赋值，此时，这个异常的catch方法不会被回调
     * 如果有立刻抛出异常的场景，使用下面的safetyCatch{}.doLaunch{}
     */
    coroutine.start(start, coroutine, block)
    return coroutine
}


@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.safetyCatch(catchBlock: (Throwable) -> Unit): SafetyCoroutine<Any> {
    return SafetyCoroutine<Any>(newCoroutineContext(EmptyCoroutineContext)).catch(catchBlock)
}


fun <T> SafetyCoroutine<T>.doLaunch(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T,
): SafetyCoroutine<T> {
    start(start, this, block)
    return this
}


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
    block: suspend CoroutineScope.() -> Unit,
) = LifeSafetyScope(this, lifeEvent, dispatcher).doLaunch(block)

/**
 * 针对View的具备生命周期感知能力的作用域
 *
 * @param dispatcher
 * @param block
 * @receiver [View]
 * @return
 */
fun View.lifeScope(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> Unit,
): ViewLifeSafetyScope {
    val scope = ViewLifeSafetyScope(this, dispatcher)
    scope.doLaunch(block)
    return scope
}
