package com.yzq.coroutine

import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * @description 有兜底异常处理的协程
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/18
 * @time 16:40
 */
@OptIn(InternalCoroutinesApi::class)
open class SafetyCoroutine<T>(parentContext: CoroutineContext) : AbstractCoroutine<T>(
    parentContext + CoroutineExceptionHandler { _, throwable -> },
    initParentJob = true, active = true
) {

    /**
     * Catch 用于异常回调
     */
    private var catch: ((Throwable) -> Unit)? = null

    override fun handleJobException(exception: Throwable): Boolean {
        Log.i("SafetyCoroutine", "exception:$exception")
        handleCoroutineException(context, exception)
        if (exception !is CancellationException) {
            catch?.invoke(exception)
        }
        return true
    }

    override fun onCompleted(value: T) {
        super.onCompleted(value)
        Log.i("SafetyCoroutine", "onCompleted")
    }

    override fun onStart() {
        super.onStart()
        Log.i("SafetyCoroutine", "onStart")
    }

    override fun onCancelling(cause: Throwable?) {
        super.onCancelling(cause)
        Log.i("SafetyCoroutine", "onCancelling")
    }

    override fun onCancelled(cause: Throwable, handled: Boolean) {
        super.onCancelled(cause, handled)
        Log.i("SafetyCoroutine", "onCancelled")
    }

    fun catch(catchBlock: (Throwable) -> Unit) = apply { this.catch = catchBlock }
}
