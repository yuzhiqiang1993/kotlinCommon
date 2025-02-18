package com.yzq.core

import androidx.lifecycle.asLiveData
import com.drake.statelayout.StateLayout
import com.hjq.toast.Toaster
import com.yzq.baseui.BaseActivity
import com.yzq.baseui.BaseFragment
import com.yzq.baseui.UIState
import com.yzq.baseui.UiStateViewModel
import com.yzq.coroutine.ext.runMain
import com.yzq.dialog.PromptDialog
import com.yzq.dialog.core.BaseDialogFragment
import com.yzq.logger.Logger

@JvmOverloads
fun BaseActivity.observeUIState(
    vm: UiStateViewModel,
    loadingDialog: BaseDialogFragment<*>? = null,
    stateLayout: StateLayout? = null,
) {
    vm.uiStateFlow.asLiveData().observe(this) {
        Logger.i("uiState:${it}")
        Logger.i("loadingDialog:${loadingDialog}")
        when (it) {
            is UIState.Init -> {
                Logger.i("初始状态，不用管")
            }

            is UIState.DismissLoadingDialog -> {
                loadingDialog?.safeDismiss()
            }

            is UIState.ShowLoadingDialog -> {
                loadingDialog?.safeShow()
            }


            is UIState.ShowContent -> {
                stateLayout?.run { runMain { showContent() } }
            }

            is UIState.ShowError -> {
                stateLayout?.run { runMain { showError(it.msg) } }
            }

            is UIState.ShowLoading -> {
                stateLayout?.run { runMain { showLoading(it.msg) } }
            }

            is UIState.ShowEmpty -> {
                stateLayout?.run { runMain { showEmpty() } }
            }

            is UIState.ShowToast -> {
                runMain { Toaster.showLong(it.content) }
            }

            is UIState.ShowDialog -> {
                runMain {
                    PromptDialog(this).content(it.content).singlePositiveBtn { v -> }.safeShow()

                }
            }

        }
    }
}

@JvmOverloads
fun BaseFragment.observeUIState(
    vm: UiStateViewModel,
    loadingDialog: BaseDialogFragment<*>? = null,
    stateLayout: StateLayout? = null,
) {
    vm.uiStateFlow.asLiveData().observe(viewLifecycleOwner) {
        when (it) {
            is UIState.Init -> {
                Logger.i("初始状态，不用管")
            }

            is UIState.DismissLoadingDialog -> {
                loadingDialog?.safeDismiss()
            }

            is UIState.ShowLoadingDialog -> {
                loadingDialog?.safeShow()
            }

            is UIState.ShowContent -> {
                stateLayout?.run { runMain { showContent() } }
            }

            is UIState.ShowError -> {
                stateLayout?.run { runMain { showError(it.msg) } }
            }

            is UIState.ShowLoading -> {
                stateLayout?.run { runMain { showLoading(it.msg) } }
            }

            is UIState.ShowEmpty -> {
                stateLayout?.run { runMain { showEmpty() } }
            }

            is UIState.ShowToast -> {
                runMain { Toaster.showLong(it.content) }
            }

            is UIState.ShowDialog -> {
                runMain {
                    PromptDialog(requireActivity()).content(it.content).singlePositiveBtn { v -> }
                        .safeShow()
                }
            }

        }
    }
}
