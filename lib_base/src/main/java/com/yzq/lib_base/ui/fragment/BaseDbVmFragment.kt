package com.yzq.lib_base.ui.fragment

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.yzq.lib_base.constants.ViewStateContstants
import com.yzq.lib_base.data.ViewStateBean
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
            lifecycleOwner = this@BaseDbVmFragment
            lifecycle.addObserver(this)
            loadState.observe(this@BaseDbVmFragment, { viewStateBean ->
                handleViewState(viewStateBean)
            })
        }


        observeViewModel()
    }


    /**
     * 处理视图UI变化显示逻辑
     * @param viewStateBean ViewStateBean
     */
    private fun handleViewState(viewStateBean: ViewStateBean) {
        val content = viewStateBean.message
        when (viewStateBean.state) {

            ViewStateContstants.showLoadingDialog -> {
                showLoadingDialog(content)
            }
            ViewStateContstants.dismissLoadingDialog -> {
                dismissLoadingDialog()
            }

            ViewStateContstants.showErrorDialog -> {
                showErrorDialog(content)
            }
            ViewStateContstants.showProgressDialog -> {
                showProgressDialog(content)
            }
            ViewStateContstants.dismissProgressDialog -> {
                dismissProgressDialog()
            }
            ViewStateContstants.changeProgress -> {
                changeProgress(content.toInt())
            }
            ViewStateContstants.showNoNet -> {
                showNoNet()
            }
            ViewStateContstants.showError -> {
                showError(content)
            }
        }

    }


}