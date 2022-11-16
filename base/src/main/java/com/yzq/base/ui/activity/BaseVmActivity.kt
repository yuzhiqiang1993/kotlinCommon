package com.yzq.base.ui.activity

import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.LogUtils
import com.yzq.base.view_model.BaseViewModel

/**
 * @description: 基于ViewModel和DataBinding的Activity基类
 * @author : XeonYu
 * @date : 2020/12/6
 * @time : 18:29
 */

abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity {
    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    lateinit var vm: VM

    abstract fun getViewModelClass(): Class<VM>

    abstract fun observeViewModel()

    override fun initViewModel() {
        vm = ViewModelProvider(this)[getViewModelClass()]

        vm.run {
            loadState.observe(this@BaseVmActivity) { viewStateBean ->
                LogUtils.i("BaseVmActivity loadState:$viewStateBean")
                stateViewManager.handleViewState(
                    viewStateBean
                )
            }
        }
        observeViewModel()
    }
}
