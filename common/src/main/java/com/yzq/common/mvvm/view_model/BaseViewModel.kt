package com.yzq.common.mvvm.view_model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yzq.common.constants.ViewStateContstants
import com.yzq.common.data.ViewStateBean


/*
* 封装的BaseViewModel
* */
abstract class BaseViewModel : ViewModel() {

    lateinit var lifecycleOwner: LifecycleOwner


    var viewStateBean = ViewStateBean()
    var loadState = MutableLiveData<ViewStateBean>()


    fun initViewModel(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    /**
     * 显示弹窗逻辑
     * @param content String
     */
    fun showloadingDialog(content: String) {
        viewStateBean.message = content
        viewStateBean.state = ViewStateContstants.showLoaddingDialog

        loadState.value = viewStateBean
    }

    fun dismissLoadingDialog() {
        viewStateBean.message = ""
        viewStateBean.state = ViewStateContstants.dismissLoaddingDialog
        loadState.value = viewStateBean
    }


    fun showErrorDialog(content: String) {
        viewStateBean.message = content
        viewStateBean.state = ViewStateContstants.showErrorDialog
        loadState.value = viewStateBean
    }

    fun showNoNet() {
        viewStateBean.message = ""
        viewStateBean.state = ViewStateContstants.showNoNet
        loadState.value = viewStateBean
    }


    fun showError(content: String) {
        viewStateBean.message = content
        viewStateBean.state = ViewStateContstants.showError
        loadState.value = viewStateBean
    }

    fun showProgressDialog(title: String) {

        viewStateBean.message = title
        viewStateBean.state = ViewStateContstants.showProgressDialog
        loadState.value = viewStateBean
    }

    fun dismissProgressDialog() {
        viewStateBean.message = ""
        viewStateBean.state = ViewStateContstants.dismissProgressDialog
        loadState.value = viewStateBean
    }

    fun changeProgress(percent: Int) {

        viewStateBean.message = percent.toString()
        viewStateBean.state = ViewStateContstants.changeProgress
        loadState.value = viewStateBean


    }

}