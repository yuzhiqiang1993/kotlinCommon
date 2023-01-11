package com.yzq.base.extend

import androidx.lifecycle.asLiveData
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.drake.statelayout.StateLayout
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.base.view_model.BaseViewModel
import com.yzq.base.view_model.UIState
import com.yzq.coroutine.safety_coroutine.runMain
import com.yzq.materialdialog.showBaseDialog
import com.yzq.widget.dialog.BubbleDialog

@JvmOverloads
fun BaseActivity.observeUIState(
    vm: BaseViewModel,
    loadingDialog: BubbleDialog? = null,
    stateLayout: StateLayout? = null,
) {
    vm.uiStateFlow.asLiveData().observe(this) {
        LogUtils.i("uiState:${it}")
        LogUtils.i("loadingDialog:${loadingDialog}")
        when (it) {
            is UIState.Init -> {
                LogUtils.i("初始状态，不用管")
            }
            is UIState.DissmissLoadingDialog -> {
                loadingDialog?.run {
                    runMain { dismissDialog() }
                }
            }
            is UIState.ShowLoadingDialog -> {
                loadingDialog?.run {
                    runMain { updateTitle(it.msg).show() }

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
    vm.uiStateFlow.asLiveData().observe(viewLifecycleOwner) {
        when (it) {
            is UIState.Init -> {
                LogUtils.i("初始状态，不用管")
            }

            is UIState.DissmissLoadingDialog -> {
                loadingDialog?.run { dismissDialog() }
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
