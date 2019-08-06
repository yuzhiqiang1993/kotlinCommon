package com.yzq.common.mvvm

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

    fun showLoading() {
        viewStateBean.message = ""
        viewStateBean.state = ViewStateContstants.showLoadding
        loadState.value = viewStateBean
    }

    fun showContent() {
        viewStateBean.message = ""
        viewStateBean.state = ViewStateContstants.showContent
        loadState.value = viewStateBean
    }

    fun showErrorDialog(content: String) {
        viewStateBean.message = content
        viewStateBean.state = ViewStateContstants.showErrorDialog
        loadState.value = viewStateBean
    }

    fun showNoNet(){
        viewStateBean.message = ""
        viewStateBean.state = ViewStateContstants.showNoNet
        loadState.value = viewStateBean
    }


    fun showError(content: String){
        viewStateBean.message = content
        viewStateBean.state = ViewStateContstants.showError
        loadState.value = viewStateBean
    }

}