package com.yzq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

/**
 * 对异常做统一的捕获处理
 *
 * @param block  代码块
 * @param onException 异常回调
 * @receiver
 * @receiver
 * @receiver
 */
inline fun ViewModel.launchScope(
    crossinline onException: (t: Throwable) -> Unit = {},
    crossinline block: suspend CoroutineScope.() -> Unit,
): Job =
    viewModelScope.launch(CoroutineExceptionHandler { _, throwable -> onException(throwable) }) {
        block()
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
inline fun ViewModel.launchSupervisorScope(
    crossinline onException: (t: Throwable) -> Unit = {},
    crossinline block: suspend CoroutineScope.() -> Unit,
) = viewModelScope.launch(
    CoroutineExceptionHandler { _, throwable ->
        onException(throwable)
    }
) {

    supervisorScope {
        block()
    }
}
