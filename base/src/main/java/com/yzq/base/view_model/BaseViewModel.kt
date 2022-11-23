package com.yzq.base.view_model

import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils

/**
 * @description BaseViewModel
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/10/31
 * @time 10:01
 */

abstract class BaseViewModel : ViewModel(), LifecycleEventObserver {

    protected val _uiState by lazy { MutableLiveData<UIState>() }
    val uiState: LiveData<UIState> = _uiState

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        LogUtils.i("onStateChanged $source === ${event.targetState}")
    }
}

sealed class UIState {
    data class ShowLoadingDialog(val msg: String) : UIState()
    data class ShowDialog(val msg: String) : UIState()
    data class ShowToast(val msg: String) : UIState()
    class DissmissLoadingDialog : UIState()
    data class ShowLoading(val msg: String) : UIState()
    data class ShowError(val msg: String) : UIState()
    class ShowEmpty : UIState()
    class ShowContent : UIState()
}
