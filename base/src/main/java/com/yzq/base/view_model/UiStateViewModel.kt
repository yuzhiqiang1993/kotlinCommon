package com.yzq.base.view_model

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.yzq.baseui.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


/**
 * @description: 维护了页面状态的view model，配合stateLayout简化代码
 * @author : yuzhiqiang
 */

abstract class UiStateViewModel : BaseViewModel() {

    /**
     * 使用stateFlow可以解决livedata必须要在主线程更新值的问题
     */
    protected val _uiStateFlow by lazy { MutableStateFlow<UIState>(UIState.Init) }
    val uiStateFlow: StateFlow<UIState> = _uiStateFlow


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

    }


}


sealed class UIState {
    data object Init : UIState()//初始状态,主要是给flow初始值用的
    data class ShowLoadingDialog(val msg: String) : UIState()
    data object DismissLoadingDialog : UIState()
    data class ShowLoading(val msg: String) : UIState()
    data class ShowError(val msg: String) : UIState()
    data class ShowToast(val content: String) : UIState()
    data class ShowDialog(val content: String) : UIState()
    data object ShowEmpty : UIState()
    data object ShowContent : UIState()

}
