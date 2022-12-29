package com.yzq.base.extend

import com.blankj.utilcode.util.ToastUtils
import com.drake.statelayout.StateLayout
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.base.view_model.BaseViewModel
import com.yzq.base.view_model.UIState
import com.yzq.coroutine.runMain
import com.yzq.materialdialog.showBaseDialog
import com.yzq.widget.dialog.BubbleDialog

@JvmOverloads
fun BaseActivity.observeUIState(
    vm: BaseViewModel,
    loadingDialog: BubbleDialog? = null,
    stateLayout: StateLayout? = null,
) {
    vm.uiState.observe(this) {
        when (it) {
            is UIState.DissmissLoadingDialog -> {
                loadingDialog?.run {
                    dismiss()
                }
            }
            is UIState.ShowLoadingDialog -> {
                loadingDialog?.run {
                    updateTitle(it.msg).show()
                }

            }
            is UIState.ShowToast -> {
                runMain { ToastUtils.showLong(it.msg) }
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
    loadingDialog: BubbleDialog? = null,
    stateLayout: StateLayout? = null,
) {
    vm.uiState.observe(this) {
        when (it) {
            is UIState.DissmissLoadingDialog -> {
                loadingDialog?.run {
                    dismiss()
                }
            }
            is UIState.ShowLoadingDialog -> {
                loadingDialog?.run {
                    updateTitle(it.msg).show()
                }

            }
            is UIState.ShowToast -> {
                runMain { ToastUtils.showLong(it.msg) }
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
