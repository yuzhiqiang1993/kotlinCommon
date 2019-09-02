package com.yzq.common.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yzq.lib_base.data.ViewStateBean
import com.yzq.lib_base.view_model.BaseViewModel


/**
 * @description: mvvm Activity基类
 * @author : yzq
 * @date   : 2018/7/12
 * @time   : 10:40
 *
 */


abstract class BaseMvvmActivity<VM : BaseViewModel> : BaseActivity() {


    lateinit var vm: VM


    abstract fun getViewModelClass(): Class<VM>

    abstract fun observeViewModel()


    override fun initViewModel() {
        vm = ViewModelProviders.of(this).get(getViewModelClass())
        vm.initViewModel(this)

        vm.loadState.observe(
            this,
            Observer<ViewStateBean> { viewStateBean -> handleViewState(viewStateBean) })

        observeViewModel()
    }


    /**
     * 处理视图UI变化显示逻辑
     * @param viewStateBean ViewStateBean
     */
    private fun handleViewState(viewStateBean: ViewStateBean) {
        val content = viewStateBean.message
        when (viewStateBean.state) {

            com.yzq.lib_base.constants.ViewStateContstants.showLoaddingDialog -> {
                showLoadingDialog(content)
            }
            com.yzq.lib_base.constants.ViewStateContstants.dismissLoaddingDialog -> {
                dismissLoadingDialog()
            }

            com.yzq.lib_base.constants.ViewStateContstants.showErrorDialog -> {
                showErrorDialog(content)
            }
            com.yzq.lib_base.constants.ViewStateContstants.showProgressDialog -> {
                showProgressDialog(content)
            }
            com.yzq.lib_base.constants.ViewStateContstants.dismissProgressDialog -> {
                dismissProgressDialog()
            }
            com.yzq.lib_base.constants.ViewStateContstants.changeProgress -> {
                changeProgress(content.toInt())
            }
            com.yzq.lib_base.constants.ViewStateContstants.showNoNet -> {
                showNoNet()
            }
            com.yzq.lib_base.constants.ViewStateContstants.showError -> {
                showError(content)
            }
        }

    }


}
