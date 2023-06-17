package com.yzq.coroutine.safety_coroutine

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.yzq.coroutine.safety_coroutine.scope.LifeSafetyScope
import com.yzq.coroutine.safety_coroutine.scope.ViewLifeSafetyScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.withContext
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
fun <T> CoroutineScope.launchSafety(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T,
): SafetyCoroutine<T> {
    val newContext = newCoroutineContext(context)
    val coroutine = SafetyCoroutine<T>(newContext)

    /**
     * 这里有个问题是如果block代码块中立刻抛出异常的话，由于catch还没赋值，此时，这个异常的catch方法不会被回调
     * 如果有立刻抛出异常的场景，在block中delay(10)或者使用lifeScope即可
     */
    coroutine.start(start, coroutine, block)
    return coroutine
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
) = LifeSafetyScope(this, lifeEvent, dispatcher).launch(block)

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
    scope.launch(block)
    return scope
}


/**
 * 在主线程中调用指定的挂起块，挂起直到完成，并返回结果。
 *
 * @param T
 * @param block
 * @receiver
 */
suspend fun <T> withMain(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Main, block)

/**
 * 在IO线程中调用指定的挂起块，挂起直到完成，并返回结果。
 * 适用于io密集型的任务，他的线程数量一般会比cpu核心数多
 * 可能会运行在default线程上，因为Dispatchers.IO底层可能会复用Dispatchers.Default中的线程
 */
suspend fun <T> withIO(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.IO, block)

/**
 * 在默认线程中调用指定的挂起块，挂起直到完成，并返回结果。
 * 适用于cpu密集型的任务，线程数量一般跟cpu核心数保持一致
 */
suspend fun <T> withDefault(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Default, block)

/**
 * 切换到没有限制的调度器
 * 除非你对这种调度比较熟悉，否则不要使用
 */
suspend fun <T> withUnconfined(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Unconfined, block)

/**
 * 在主线程上运行
 *
 * @param block
 * @receiver
 */
fun runMain(block: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        block()
    } else {
        Handler(Looper.getMainLooper()).post { block() }
    }
}


internal fun postDelayed(
    looper: Looper = Looper.getMainLooper(),
    deleyMillis: Long = 10,
    block: () -> Unit
) {
    Handler(looper).postDelayed(block, deleyMillis)
}
