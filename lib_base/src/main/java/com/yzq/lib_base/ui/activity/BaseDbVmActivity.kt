package com.yzq.lib_base.ui.activity

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.LogUtils
import com.yzq.lib_base.view_model.BaseViewModel


/**
 * @description: 基于ViewModel和DataBinding的Activity基类
 * @author : XeonYu
 * @date   : 2020/12/6
 * @time   : 18:29
 */

abstract class BaseDbVmActivity<VDB : ViewDataBinding, VM : BaseViewModel> :
    BaseDataBindingActivity<VDB>() {

    lateinit var vm: VM

    abstract fun getViewModelClass(): Class<VM>

    abstract fun observeViewModel()


    override fun initViewModel() {
        vm = ViewModelProvider(this).get(getViewModelClass())
        with(vm) {
            lifecycleOwner = this@BaseDbVmActivity
            lifecycle.addObserver(this)
            loadState.observe(this@BaseDbVmActivity) { viewStateBean ->
                LogUtils.i("BaseDbVmActivity loadState:${viewStateBean}")
                stateViewManager.handleViewState(
                    viewStateBean
                )
            }
        }
        observeViewModel()
    }


}