package com.yzq.lib_base.ui.activity

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.yzq.lib_base.view_model.BaseViewModel


/**
 * @description: 基于ViewModel和ViewBinding的Activity基类
 * @author : XeonYu
 * @date   : 2020/12/6
 * @time   : 18:29
 */

abstract class BaseVbVmActivity<VB : ViewBinding, VM : BaseViewModel> :
    BaseViewBindingActivity<VB>() {

    lateinit var vm: VM

    abstract fun getViewModelClass(): Class<VM>

    abstract fun observeViewModel()


    override fun initViewModel() {
        vm = ViewModelProvider(this).get(getViewModelClass())
        with(vm) {
            lifecycleOwner = this@BaseVbVmActivity
            lifecycle.addObserver(this)
            loadState.observe(
                lifecycleOwner
            ) { viewStateBean -> stateViewManager.handleViewState(viewStateBean) }
        }
        observeViewModel()
    }


}