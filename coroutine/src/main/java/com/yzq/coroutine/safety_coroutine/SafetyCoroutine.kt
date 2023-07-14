package com.yzq.coroutine.safety_coroutine

import com.yzq.logger.Logger
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.handleCoroutineException
import kotlin.coroutines.CoroutineContext


/**
 * @description 有异常兜底的自定义协程实现
 *
 * 主要针对已有的协程作用域进行扩展实现，使得协程作用域能够安全的执行代码，且能够捕获到作用域中的异常
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */


private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Logger.i("SafetyCoroutine CoroutineExceptionHandler: ${throwable.message}")
}

@OptIn(InternalCoroutinesApi::class)
class SafetyCoroutine<T>(parentContext: CoroutineContext) : AbstractCoroutine<T>(
    parentContext + coroutineExceptionHandler,
    initParentJob = true,
    active = true
) {


    private val TAG = "SafetyCoroutine:${this.hashCode()}"

    /**
     * Catch 用于异常回调
     */
    private var onCatch: ((Throwable) -> Unit)? = null


    private var onFinally: ((Throwable?) -> Unit)? = null


    /**
     * 处理父协程最终的异常信息，也就是只有会影响当前协程的异常才会被处理到，
     * 如果代码块中有supervisorScope，并且在supervisorScope中的子协程中有异常抛出，但是由于supervisorScope的特性，其子协程的异常不会影响到父协程，这里是不会处理到的
     * 但是在上面的coroutineExceptionHandler中是会被处理到的
     * @param exception Throwable
     * @return Boolean
     */
    override fun handleJobException(exception: Throwable): Boolean {
        Logger.i("$TAG  handleJobException: ${exception.message}")
        handleCoroutineException(context, exception)
        if (exception !is CancellationException) {
            invokeCatch(exception)
        }
        return true
    }


    /**
     * onCancelling是一定会执行的，不管正常完成还是异常被取消都会执行，源码注释如下
     * 此函数在因任何原因被取消或完成时立即调用一次，类似于 invokeOnCompletion onCancelling true设置为 。
     * 参数的含义 cause ：
     * 原因是 null 作业已正常完成。
     * 原因是作业正常取消的实例CancellationException。不应将其视为错误。特别是，不应将其报告给错误日志。
     * 否则，作业已被取消或失败，但出现异常。
     * 指定的 cause 不是此作业的最终取消原因。作业在失败时可能会产生其他异常，最终原因可能有所不同。
     */
    override fun onCancelling(cause: Throwable?) {
        super.onCancelling(cause)
        Logger.i("$TAG   onCancelling")
    }

    /**
     * 代码块被取消时会被执行
     */
    override fun onCancelled(cause: Throwable, handled: Boolean) {
        super.onCancelled(cause, handled)
        Logger.i("$TAG   onCancelled")
        invokeFinally(cause)
    }

    /**
     * 代码块正常完成时会被执行
     */
    override fun onCompleted(value: T) {
        super.onCompleted(value)
        Logger.i("$TAG  onCompleted")
        invokeFinally()
    }


    private fun invokeCatch(exception: Throwable) {
        Logger.i("$TAG  handleJobException invokeCatch exception:$exception")
        if (onCatch == null) {
            /*这里延时10ms执行*/
            postDelayed {
                Logger.i("$TAG  延迟执行 invokeCatch onCatch")
                onCatch?.invoke(exception)
            }
        } else {
            Logger.i("$TAG  invokeCatch onCatch")
            onCatch?.invoke(exception)
        }
    }


    private fun invokeFinally(cause: Throwable? = null) {
        if (onFinally == null) {
            postDelayed {
                Logger.i("$TAG  延迟执行 invokeFinally")
                onFinally?.invoke(cause)
            }
        } else {
            Logger.i("$TAG  invokeFinally")
            onFinally?.invoke(cause)
        }
    }

    fun catch(catchBlock: (Throwable) -> Unit) = apply {
        Logger.i("$TAG  给catch赋值")
        this.onCatch = catchBlock
    }

    fun finally(block: ((Throwable?) -> Unit)) = apply {
        Logger.i("$TAG  给 fininsh 赋值")
        this.onFinally = block
    }
}
