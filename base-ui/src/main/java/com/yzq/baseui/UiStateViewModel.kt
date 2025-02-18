package com.yzq.baseui

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

}



