package com.yzq.common.mvvm.view_model

import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.yzq.data_constants.constants.ViewStateContstants
import com.yzq.data_constants.data.base.ViewStateBean


/*
* 封装的BaseViewModel
* */
abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    lateinit var lifecycleOwner: LifecycleOwner


    var viewStateBean = ViewStateBean()
    var loadState = MutableLiveData<ViewStateBean>()


    fun initViewModel(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
        this.lifecycleOwner.lifecycle.addObserver(this)
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


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onDestory() {
        LogUtils.i("lifecycleOwner：${lifecycleOwner}-->Lifecycle.Event.ON_DESTROY")

    }


}