package com.yzq.coroutine.safety_coroutine.scope

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

/**
 * 自定义的协程作用域,具备生命周期感知能力和全局异常兜底功能，可自动取消协程，捕获异常
 * @param lifecycleOwner 生命周期持有者 不传则不具备自动取消的功能
 * @param lifeEvent 生命周期事件, 默认为 [Lifecycle.Event.ON_DESTROY] 下取消协程作用域
 */
open class LifeSafetyScope(
    lifecycleOwner: LifecycleOwner? = null,
    lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
) : CoroutineScope, Closeable {

    private val tag: String = "LifeSafetyScope"

    init {
        lifecycleOwner?.lifecycle?.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (lifeEvent == event) cancel()
            }
        })
    }

    /**
     * Catch 用于异常回调
     */
    protected var onCatch: (LifeSafetyScope.(t: Throwable) -> Unit)? = null

    /**
     * Finally 用于最终回调
     */
    protected var onFinally: (LifeSafetyScope.(t: Throwable?) -> Unit)? = null

    /**
     * Exception handler 异常兜底
     */
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        if (throwable !is CancellationException) {
            catch(throwable)
        }
    }

    override val coroutineContext: CoroutineContext =
        dispatcher + exceptionHandler + SupervisorJob()


    open fun launch(block: suspend CoroutineScope.() -> Unit): LifeSafetyScope {
        Log.i(tag, "doLaunch")
        launch(coroutineContext) {
            Log.i(tag, "launch")
            block()
        }.invokeOnCompletion {
            /*不管协程是正常完成还有出现异常，最终都会走到该方法*/
            finally(it)
        }
        return this@LifeSafetyScope
    }


    protected open fun catch(throwable: Throwable) {
        Log.i(tag, "catch:${onCatch}")
        onCatch?.invoke(this@LifeSafetyScope, throwable)
    }

    /**
     *
     * @param throwable 如果发生异常导致作用域执行完毕, 则参数为该异常对象, 正常结束则为null
     */
    protected open fun finally(throwable: Throwable?) {
        onFinally?.invoke(this@LifeSafetyScope, throwable)
    }

    /**
     * 当作用域内发生异常时回调
     */
    open fun catch(block: LifeSafetyScope.(t: Throwable) -> Unit = {}): LifeSafetyScope {
        Log.i(tag, "给catch赋值")
        this.onCatch = block
        return this
    }

    /**
     * 无论正常或者异常结束都将最终执行
     * 如果出现异常会携带异常信息 正常执行则为null
     */
    open fun finally(block: LifeSafetyScope.(t: Throwable?) -> Unit = {}): LifeSafetyScope {
        this.onFinally = block
        return this
    }

    override fun close() {
        cancel()
    }
}
