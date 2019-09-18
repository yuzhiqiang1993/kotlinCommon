package com.yzq.lib_base.view_model

import android.text.TextUtils
import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.JsonParseException
import com.yzq.lib_base.data.ViewStateBean
import com.yzq.lib_constants.BaseConstants
import com.yzq.lib_constants.ViewStateContstants
import kotlinx.coroutines.*
import org.json.JSONException
import java.net.SocketTimeoutException


/*
* 封装的BaseViewModel
* */
abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    lateinit var lifecycleOwner: LifecycleOwner


    var viewStateBean = ViewStateBean()
    var loadState = MutableLiveData<ViewStateBean>()


    fun launchLoadingDialog(block: suspend CoroutineScope.() -> Unit) {

        viewModelScope.launch {
            if (!NetworkUtils.isConnected()) {
                showNoNet()
                cancel()
                return@launch
            }
            showloadingDialog(BaseConstants.LOADING)

            try {

                block()


            } catch (e: Exception) {
                dismissLoadingDialog()
                e.printStackTrace()
                if (e is JSONException || e is JsonParseException) {
                    showErrorDialog(BaseConstants.PARSE_DATA_ERROE)
                } else if (e is SocketTimeoutException) {
                    showErrorDialog(BaseConstants.SERVER_TIMEOUT)
                } else {
                    val msg =
                        if (TextUtils.isEmpty(e.message)) BaseConstants.UNKONW_ERROR else e.message!!
                    showErrorDialog(msg)
                }

            } finally {
                cancel()

            }

        }


    }

    fun launchLoading(block: suspend CoroutineScope.() -> Unit) {

        viewModelScope.launch {
            if (!NetworkUtils.isConnected()) {
                showNoNet()
                cancel()
                return@launch
            }

            try {
                block()
            } catch (e: Exception) {

                e.printStackTrace()

                if (e is JSONException || e is JsonParseException) {
                    showError(BaseConstants.PARSE_DATA_ERROE)
                } else if (e is SocketTimeoutException) {
                    showError(BaseConstants.SERVER_TIMEOUT)
                } else {
                    val msg =
                        if (TextUtils.isEmpty(e.message)) BaseConstants.UNKONW_ERROR else e.message!!
                    showError(msg)
                }

            } finally {
                cancel()
            }


        }


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

        lifecycleOwner.lifecycle.removeObserver(this)
        LogUtils.i("lifecycleOwner：${lifecycleOwner}-->ON_DESTROY")

    }


}