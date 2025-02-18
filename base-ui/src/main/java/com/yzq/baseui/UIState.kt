package com.yzq.baseui

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