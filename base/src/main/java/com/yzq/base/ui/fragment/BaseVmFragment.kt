package com.yzq.base.ui.fragment

import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.yzq.base.view_model.BaseViewModel


/**
 * @description: 带ViewModel的Fragment基类
 * @author : yzq
 * @date   : 2018/7/12
 * @time   : 10:40
 *
 */
abstract class BaseVmFragment<VM : BaseViewModel>(@LayoutRes contentLayoutId: Int) :
    BaseFragment(contentLayoutId) {


    protected lateinit var vm: VM

    abstract fun getViewModelClass(): Class<VM>

    abstract fun observeViewModel()


    override fun initViewModel() {
        vm = ViewModelProvider(this)[getViewModelClass()]

        vm.run {
            loadState.observe(viewLifecycleOwner) { viewStateBean ->
                stateViewManager.handleViewState(viewStateBean)
            }
        }
        observeViewModel()
    }


}
