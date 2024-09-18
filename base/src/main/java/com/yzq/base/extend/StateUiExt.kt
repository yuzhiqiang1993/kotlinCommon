package com.yzq.base.extend

import androidx.lifecycle.asLiveData
import com.drake.statelayout.StateLayout
import com.hjq.toast.Toaster
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.base.view_model.BaseViewModel
import com.yzq.base.view_model.UIState
import com.yzq.coroutine.safety_coroutine.runMain
import com.yzq.dialog.core.BaseDialogFragment
import com.yzq.dialog.showBaseDialog
import com.yzq.logger.Logger

@JvmOverloads
fun BaseActivity.observeUIState(
    vm: BaseViewModel,
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

            is UIState.ShowToast -> {
                runMain { Toaster.showLong(it.msg) }
            }

            is UIState.ShowDialog -> {
                runMain { showBaseDialog(message = it.msg) }
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

        }
    }
}

@JvmOverloads
fun BaseFragment.observeUIState(
    vm: BaseViewModel,
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

            is UIState.ShowToast -> {
                runMain { Toaster.showLong(it.msg) }
            }

            is UIState.ShowDialog -> {
                runMain { requireActivity().showBaseDialog(message = it.msg) }
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
        }
    }
}
