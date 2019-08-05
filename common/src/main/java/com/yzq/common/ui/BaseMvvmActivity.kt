package com.yzq.common.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yzq.common.constants.ViewStateContstants
import com.yzq.common.data.ViewStateBean
import com.yzq.common.mvvm.BaseViewModel


/**
 * @description: mvvm Activity基类
 * @author : yzq
 * @date   : 2018/7/12
 * @time   : 10:40
 *
 */


abstract class BaseMvvmActivity<VM : BaseViewModel> : BaseActivity() {


    lateinit var viewModel: VM


    fun initViewModel(clazz: Class<VM>) {
        @Suppress("UNCHECKED_CAST")

        viewModel = ViewModelProviders.of(this).get(clazz)
        viewModel.initViewModel(this)

        viewModel.loadState.observe(this, object : Observer<ViewStateBean> {
            override fun onChanged(viewStateBean: ViewStateBean) {
                handleViewState(viewStateBean)
            }

        })
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
            ViewStateContstants.showLoadding -> {
                showLoadding()
            }
            ViewStateContstants.showContent -> {
                showContent()
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
