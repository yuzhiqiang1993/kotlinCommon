package com.yzq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope


/**
 * 统一的异常处理，需要注意的点是如果发生异常，onFinish 是在 onException 之前执行的
 *
 * @param block  代码块
 * @param onException 异常回调
 * @param onFinish  结束回调
 * @receiver
 * @receiver
 * @receiver
 */
inline fun ViewModel.launchFullLife(
    crossinline onException: (t: Throwable) -> Unit = {},
    crossinline onFinish: () -> Unit = {},
    crossinline block: suspend CoroutineScope.() -> Unit,
) {
    viewModelScope.launch(CoroutineExceptionHandler { _, throwable -> onException(throwable) }) {
        try {
            block()
        } finally {
            onFinish()
        }
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

    viewModelScope.launch(CoroutineExceptionHandler { _, throwable -> onException(throwable) }) {
        try {
            supervisorScope { block() }
        } finally {
            onFinish()
        }
    }

}