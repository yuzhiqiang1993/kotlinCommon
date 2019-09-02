package com.yzq.lib_base.view_model

import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.yzq.lib_base.data.ViewStateBean
import com.yzq.lib_base.constants.ViewStateContstants


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


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onCreate() {
        LogUtils.i("lifecycleOwner：${lifecycleOwner}-->ON_CREATE")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onStart() {
        LogUtils.i("lifecycleOwner：${lifecycleOwner}-->ON_START")

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onResume() {
        LogUtils.i("lifecycleOwner：${lifecycleOwner}-->ON_RESUME")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onPause() {
        LogUtils.i("lifecycleOwner：${lifecycleOwner}-->ON_PAUSE")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected open fun onStop() {
        LogUtils.i("lifecycleOwner：${lifecycleOwner}-->ON_STOP")

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onDestory() {
        LogUtils.i("lifecycleOwner：${lifecycleOwner}-->ON_DESTROY")

    }


}