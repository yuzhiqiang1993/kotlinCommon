package com.yzq.lib_base.ui.activity

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.LogUtils
import com.yzq.lib_base.constants.ViewStateContstants
import com.yzq.lib_base.data.ViewStateBean
import com.yzq.lib_base.view_model.BaseViewModel


/**
 * @description: 基于ViewModel和DataBinding的Activity基类
 * @author : XeonYu
 * @date   : 2020/12/6
 * @time   : 18:29
 */

abstract class BaseDbVmActivity<DB : ViewDataBinding, VM : BaseViewModel> :
    BaseDataBindingActivity<DB>() {

    lateinit var vm: VM

    abstract fun getViewModelClass(): Class<VM>

    abstract fun observeViewModel()


    override fun initViewModel() {
        vm = ViewModelProvider(this).get(getViewModelClass())
        with(vm) {
            lifecycleOwner = this@BaseDbVmActivity
            lifecycle.addObserver(this)
            loadState.observe(this@BaseDbVmActivity) { viewStateBean ->
                handleViewState(
                    viewStateBean
                )
            }
        }
        observeViewModel()
    }


    /**
     * 处理视图UI变化显示逻辑
     * @param viewStateBean ViewStateBean
     */
    private fun handleViewState(viewStateBean: ViewStateBean) {

        LogUtils.i("handleViewState${viewStateBean}")
        val content = viewStateBean.message
        when (viewStateBean.state) {
            ViewStateContstants.showLoadingDialog -> {
                showLoadingDialog(content)
            }
            ViewStateContstants.dismissLoadingDialog -> {
                LogUtils.i("dismissLoadingDialog")
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