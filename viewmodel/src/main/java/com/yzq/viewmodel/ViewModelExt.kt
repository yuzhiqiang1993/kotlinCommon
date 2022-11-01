package com.yzq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


/**
 * 对异常做统一的捕获处理
 *
 * @param block  代码块
 * @param onException 异常回调
 * @param onFinish  结束回调
 * @receiver
 * @receiver
 * @receiver
 */
inline fun ViewModel.launchScope(
    crossinline onException: (t: Throwable) -> Unit = {},
    crossinline onFinish: () -> Unit = {},
    crossinline block: suspend CoroutineScope.() -> Unit,
) {
    viewModelScope.launch(CoroutineExceptionHandler { _, throwable -> onException(throwable) }) {
        block()
    }.invokeOnCompletion {
        /*invokeOnCompletion:监听协程的结束事件，取消以及完成都会执行*/
        onFinish()
    }
}


/**
 * Launch supervisor scope
 *
 * @param onException 异常回调
 * @param onFinish 结束回调
 * @param block 代码块
 * @receiver
 * @receiver
 * @receiver
 */
inline fun ViewModel.launchSupervisor(
    crossinline onException: (t: Throwable) -> Unit = {},
    crossinline onFinish: () -> Unit = {},
    crossinline block: suspend CoroutineScope.() -> Unit,
) {

    viewModelScope.launch(SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
        onException(throwable)
    }) {
        block()
    }.invokeOnCompletion {
        onFinish()
    }


}


