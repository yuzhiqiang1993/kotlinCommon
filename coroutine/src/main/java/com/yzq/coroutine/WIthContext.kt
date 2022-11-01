package com.yzq.coroutine

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


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