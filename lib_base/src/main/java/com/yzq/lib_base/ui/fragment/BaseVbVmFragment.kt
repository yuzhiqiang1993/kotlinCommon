package com.yzq.lib_base.ui.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.yzq.lib_base.view_model.BaseViewModel


/**
 * @description: mvvm Fragment基类
 * @author : yzq
 * @date   : 2018/7/12
 * @time   : 10:40
 *
 */


abstract class BaseVbVmFragment<VB : ViewBinding, VM : BaseViewModel> :
    BaseViewBindingFragment<VB>() {


    protected lateinit var vm: VM

    abstract fun getViewModelClass(): Class<VM>

    abstract fun observeViewModel()


    override fun initViewModel() {
        vm = ViewModelProvider(this).get(getViewModelClass())

        vm.run {
            lifecycle.addObserver(this)
            loadState.observe(this@BaseVbVmFragment) { viewStateBean ->
                stateViewManager.handleViewState(viewStateBean)
            }
        }
        observeViewModel()
    }


}
