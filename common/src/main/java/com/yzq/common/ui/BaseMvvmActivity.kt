package com.yzq.common.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yzq.data_constants.constants.ViewStateContstants
import com.yzq.data_constants.data.base.ViewStateBean
import com.yzq.common.mvvm.view_model.BaseViewModel


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
        @Suppress("UNCHECKED_CAST")

        vm = ViewModelProviders.of(this).get(getViewModelClass())
        vm.initViewModel(this)

        vm.loadState.observe(this, Observer<ViewStateBean> { viewStateBean -> handleViewState(viewStateBean) })

        observeViewModel()
    }


    /**
     * 处理视图UI变化显示逻辑
     * @param viewStateBean ViewStateBean
     */
    private fun handleViewState(viewStateBean: ViewStateBean) {
        val content = viewStateBean.message
        when (viewStateBean.state) {

            ViewStateContstants.showLoaddingDialog -> {
                showLoadingDialog(content)
            }
            ViewStateContstants.dismissLoaddingDialog -> {
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