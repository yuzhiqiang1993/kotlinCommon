package com.yzq.lib_base.ui.fragment

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.yzq.lib_base.view_model.BaseViewModel


/**
 * @description: 基于DataBinding和ViewModel的Fragment基类
 * @author : XeonYu
 * @date   : 2020/12/6
 * @time   : 22:14
 */

abstract class BaseDbVmFragment<DB : ViewDataBinding, VM : BaseViewModel> :
    BaseDataBindingFragment<DB>() {


    protected lateinit var vm: VM

    abstract fun getViewModelClass(): Class<VM>

    abstract fun observeViewModel()


    override fun initViewModel() {
        vm = ViewModelProvider(this).get(getViewModelClass())

        with(vm) {
            lifecycle.addObserver(this)
            loadState.observe(this@BaseDbVmFragment) { viewStateBean ->
                stateViewManager.handleViewState(viewStateBean)
            }
        }


        observeViewModel()
    }


}