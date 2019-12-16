package com.yzq.lib_base.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yzq.lib_base.constants.ViewStateContstants
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


        vm = ViewModelProvider(this).get(getViewModelClass())
        with(vm) {
            lifecycleOwner = this@BaseMvvmActivity
            lifecycle.addObserver(this)
            loadState.observe(
                lifecycleOwner,
                Observer<ViewStateBean> { viewStateBean -> handleViewState(viewStateBean) })
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
