package com.yzq.coroutine.scope

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.yzq.coroutine.runMain
import kotlinx.coroutines.*
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

/**
 * 封装的协程作用域,具备自动取消以及异常捕获等功能
 * @param lifecycleOwner 生命周期持有者
 * @param lifeEvent 生命周期事件, 默认为 [Lifecycle.Event.ON_DESTROY] 下取消协程作用域
 */
@Suppress("unused", "MemberVisibilityCanBePrivate", "NAME_SHADOWING")
open class AndroidScope(
    lifecycleOwner: LifecycleOwner? = null,
    lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
) : CoroutineScope, Closeable {

    init {
        runMain {
            lifecycleOwner?.lifecycle?.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (lifeEvent == event) cancel()
                }
            })
        }
    }

    /**
     * Catch 用于异常回调
     */
    protected var catch: (AndroidScope.(Throwable) -> Unit)? = null

    /**
     * Finally 用于最终回调
     */
    protected var finally: (AndroidScope.(Throwable?) -> Unit)? = null

    /**
     * Exception handler 异常兜底
     */
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        catch(throwable)
    }

    override val coroutineContext: CoroutineContext =
        dispatcher + exceptionHandler + SupervisorJob()

    open fun doLaunch(block: suspend CoroutineScope.() -> Unit): AndroidScope {

        launch(coroutineContext) {
            block()
        }.invokeOnCompletion {
            finally(it)
        }
        return this
    }

    protected open fun catch(throwable: Throwable) {
        catch?.invoke(this@AndroidScope, throwable)
    }

    /**
     * @param throwable 如果发生异常导致作用域执行完毕, 则参数为该异常对象, 正常结束则为null
     */
    protected open fun finally(throwable: Throwable?) {
        finally?.invoke(this@AndroidScope, throwable)
    }

    /**
     * 当作用域内发生异常时回调
     */
    open fun catch(block: AndroidScope.(Throwable) -> Unit = {}): AndroidScope {
        this.catch = block
        return this
    }

    /**
     * 无论正常或者异常结束都将最终执行
     * 如果出现异常会携带异常信息 正常执行则为null
     */
    open fun finally(block: AndroidScope.(Throwable?) -> Unit = {}): AndroidScope {
        this.finally = block
        return this
    }

    open fun cancel(cause: CancellationException? = null) {
        val job = coroutineContext[Job]
            ?: error("Scope cannot be cancelled because it does not have a job: $this")
        job.cancel(cause)
        Log.i("cancel", "====cancel:$job")
    }

    open fun cancel(
        message: String,
        cause: Throwable? = null,
    ) = cancel(CancellationException(message, cause))

    override fun close() {
        Log.i("close", "====close")
        cancel()
    }
}
