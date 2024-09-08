package com.yzq.base.view_model

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.yzq.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @description BaseViewModel
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/10/31
 * @time 10:01
 */

abstract class BaseViewModel : ViewModel(), LifecycleEventObserver {


    protected val TAG = "${this.javaClass.simpleName}-${this.hashCode()}"

    /**
     * 使用stateFlow可以解决livedata必须要在主线程更新值的问题
     */
    protected val _uiStateFlow by lazy { MutableStateFlow<UIState>(UIState.Init) }
    val uiStateFlow: StateFlow<UIState> = _uiStateFlow

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Logger.i("onStateChanged $event-->${event.targetState}")
    }
}

sealed class UIState {
    data object Init : UIState()//初始状态,主要是给flow初始值用的
    data class ShowLoadingDialog(val msg: String) : UIState()
    data class ShowDialog(val msg: String) : UIState()
    data class ShowToast(val msg: String) : UIState()
    data object DissmissLoadingDialog : UIState()
    data class ShowLoading(val msg: String) : UIState()
    data class ShowError(val msg: String) : UIState()
    class ShowEmpty : UIState()
    class ShowContent : UIState()
}
