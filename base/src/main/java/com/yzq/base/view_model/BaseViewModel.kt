package com.yzq.base.view_model

import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.yzq.base.ui.state_view.constants.ViewStateContstants
import com.yzq.base.ui.state_view.data.ViewStateBean
import com.yzq.viewmodel.launchLife
import com.yzq.viewmodel.launchSupervisor
import kotlinx.coroutines.*
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo


/**
 * @description BaseViewModel
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/10/31
 * @time    10:01
 */

abstract class BaseViewModel : ViewModel(), LifecycleEventObserver {
    private val viewStateBean by lazy { ViewStateBean() }
    val loadState by lazy { MutableLiveData<ViewStateBean>() }


    /**
     * Launch loading dialog
     *
     * @param loadText
     * @param checkNetWork
     * @param onException
     * @param block
     * @receiver
     * @receiver
     */
    fun launchLoadingDialog(
        loadText: String = ViewStateContstants.LOADING,
        checkNetWork: Boolean = false,
        onException: (t: Throwable) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ) {

        launchLife(onException = onException, onFinish = { dismissLoadingDialog() }) {
            if (checkNetWork && !NetworkUtils.isConnected()) {
                showNoNet()
                cancel()
                return@launchLife
            }
            showloadingDialog(loadText)
            delay(1500)
            block()

        }


    }


    fun launchLoading(
        checkNetWork: Boolean = false,
        onException: (t: Throwable) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ) {

        launchLife(
            onException = {
                LogUtils.i("异常了")
                showError(it.message ?: "未知异常")
                onException(it)
            },
            onFinish = {
                LogUtils.i("结束了...")
            }
        ) {
            if (checkNetWork && !NetworkUtils.isConnected()) {
                showNoNet()
                cancel()
                return@launchLife
            }
            showLoading()
            block()
        }


    }


    fun launchProgressDialog(
        url: String,
        title: String,
        checkNetWork: Boolean = true,
        onException: (t: Throwable) -> Unit = {},
        onProgressException: (id: Long, t: Throwable?) -> Unit = { id: Long, throwable: Throwable? -> },
        block: suspend CoroutineScope.() -> Unit
    ) {


        launchLife(
            onException = onException,
            onFinish = { dismissProgressDialog() }) {
            if (checkNetWork && !NetworkUtils.isConnected()) {
                showNoNet()
                cancel()
                return@launchLife
            }

            ProgressManager.getInstance()
                .addResponseListener(url, object : ProgressListener {
                    override fun onProgress(progressInfo: ProgressInfo?) {
                        changeProgress(progressInfo!!.percent)
                    }

                    override fun onError(id: Long, e: Exception?) {

                        dismissProgressDialog()
                        onProgressException(id, e)

                    }
                })

            showProgressDialog(title)
            block()

        }


    }


    fun launchHttpSupervisor(
        checkNetWork: Boolean = false,
        onException: (t: Throwable) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ) {

        launchSupervisor(onException = onException) {
            if (checkNetWork && !NetworkUtils.isConnected()) {
                showNoNet()
                cancel()
                return@launchSupervisor
            }

            block()

        }

    }

    private fun showLoading() {
        viewStateBean.message = ""
        viewStateBean.state = ViewStateContstants.showLoading
        loadState.value = viewStateBean
    }

    /**
     * 显示弹窗逻辑
     * @param content String
     */
    private fun showloadingDialog(content: String) {
        viewStateBean.message = content
        viewStateBean.state = ViewStateContstants.showLoadingDialog
        viewModelScope.launch(Dispatchers.Main) {
            loadState.value = viewStateBean
        }

    }

    private fun dismissLoadingDialog() {
        viewStateBean.message = ""
        viewStateBean.state = ViewStateContstants.dismissLoadingDialog
        viewModelScope.launch(Dispatchers.Main) {
            loadState.value = viewStateBean
        }

    }

    private fun showErrorDialog(content: String) {
        viewStateBean.message = content
        viewStateBean.state = ViewStateContstants.showErrorDialog
        viewModelScope.launch(Dispatchers.Main) {
            loadState.value = viewStateBean
        }
    }

    private fun showNoNet() {
        viewStateBean.message = ""
        viewStateBean.state = ViewStateContstants.showNoNet
        viewModelScope.launch(Dispatchers.Main) {
            loadState.value = viewStateBean
        }
    }

    private fun showError(content: String) {
        viewStateBean.message = content
        viewStateBean.state = ViewStateContstants.showError
        viewModelScope.launch(Dispatchers.Main) {
            loadState.value = viewStateBean
        }
    }

    private fun showProgressDialog(title: String) {
        viewStateBean.message = title
        viewStateBean.state = ViewStateContstants.showProgressDialog
        viewModelScope.launch(Dispatchers.Main) {
            loadState.value = viewStateBean
        }

    }

    fun dismissProgressDialog() {
        viewStateBean.message = ""
        viewStateBean.state = ViewStateContstants.dismissProgressDialog
        viewModelScope.launch(Dispatchers.Main) {
            loadState.value = viewStateBean
        }
    }

    fun changeProgress(percent: Int) {
        viewStateBean.message = percent.toString()
        viewStateBean.state = ViewStateContstants.changeProgress
        viewModelScope.launch(Dispatchers.Main) {
            loadState.value = viewStateBean
        }

    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        LogUtils.i("onStateChanged $source === ${event.targetState}")
    }

}