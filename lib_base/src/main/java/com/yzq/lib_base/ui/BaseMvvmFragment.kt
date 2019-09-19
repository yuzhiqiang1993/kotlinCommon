package com.yzq.lib_base.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yzq.lib_base.data.ViewStateBean
import com.yzq.lib_base.view_model.BaseViewModel
import com.yzq.lib_constants.ViewStateContstants


/**
 * @description: mvvm Fragment基类
 * @author : yzq
 * @date   : 2018/7/12
 * @time   : 10:40
 *
 */


abstract class BaseMvvmFragment<VM : BaseViewModel> : BaseFragment() {


    lateinit var vm: VM


    abstract fun getViewModelClass(): Class<VM>

    abstract fun observeViewModel()


    override fun initViewModel() {
        @Suppress("UNCHECKED_CAST")

        vm = ViewModelProvider(this).get(getViewModelClass())


        vm.let {
            vm.lifecycleOwner = this
            lifecycle.addObserver(it)
        }

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
