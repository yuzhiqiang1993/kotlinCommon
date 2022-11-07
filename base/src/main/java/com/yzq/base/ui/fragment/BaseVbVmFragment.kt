package com.yzq.base.ui.fragment

import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.yzq.base.view_model.BaseViewModel


/**
 * @description: mvvm Fragment基类
 * @author : yzq
 * @date   : 2018/7/12
 * @time   : 10:40
 *
 */


abstract class BaseVbVmFragment<VB : ViewBinding, VM : BaseViewModel>(@LayoutRes contentLayoutId: Int) :
    BaseViewBindingFragment<VB>(contentLayoutId) {


    protected lateinit var vm: VM

    abstract fun getViewModelClass(): Class<VM>

    abstract fun observeViewModel()


    override fun initViewModel() {
        vm = ViewModelProvider(this)[getViewModelClass()]

        vm.run {
            lifecycle.addObserver(this)
            loadState.observe(this@BaseVbVmFragment) { viewStateBean ->
                stateViewManager.handleViewState(viewStateBean)
            }
        }
        observeViewModel()
    }


}
