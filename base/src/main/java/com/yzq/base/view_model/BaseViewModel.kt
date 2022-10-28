package com.yzq.base.view_model

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.yzq.base.ui.state_view.constants.ViewStateContstants
import com.yzq.base.ui.state_view.data.ViewStateBean
import kotlinx.coroutines.*
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo

/*
* 封装的BaseViewModel
* */
@SuppressLint("MissingPermission")
abstract class BaseViewModel : ViewModel(), LifecycleEventObserver {
    private val viewStateBean by lazy { ViewStateBean() }
    val loadState by lazy { MutableLiveData<ViewStateBean>() }

    private val loadingDialogCoroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            LogUtils.e("loadingDialogCoroutineExceptionHandler=====>:${throwable.message}")
            throwable.printStackTrace()
            /*隐藏弹窗*/
            dismissLoadingDialog()
            val msg =
                if (TextUtils.isEmpty(throwable.message)) ViewStateContstants.UNKONW_ERROR else throwable.message!!
            showErrorDialog(msg)
        }

    fun launchLoadingDialog(
        loadText: String = ViewStateContstants.LOADING,
        checkNetWork: Boolean = true,
        exceptionHandler: CoroutineExceptionHandler = loadingDialogCoroutineExceptionHandler,
        block: suspend CoroutineScope.() -> Unit
    ) {

        viewModelScope.launch(exceptionHandler) {

            if (checkNetWork && !NetworkUtils.isConnected()) {
                showNoNet()
                cancel()
                return@launch
            }
            showloadingDialog(loadText)

            block()

            dismissLoadingDialog()

        }

    }

    /*异常处理*/
    private val loadingCoroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        LogUtils.e("异常了=====>:${e.message}")
        e.printStackTrace()

        val msg =
            if (TextUtils.isEmpty(e.message)) ViewStateContstants.UNKONW_ERROR else e.message!!
        showError(msg)
    }

    fun launchLoading(
        checkNetWork: Boolean = true,
        exceptionHandler: CoroutineExceptionHandler = loadingCoroutineExceptionHandler,
        block: suspend CoroutineScope.() -> Unit
    ) {

        viewModelScope.launch(exceptionHandler) {
            if (checkNetWork && !NetworkUtils.isConnected()) {
                showNoNet()
                cancel()
                return@launch
            }

            showLoading()

            block()
        }
    }

    private val progressDialogCoroutineExceptionHandler = CoroutineExceptionHandler { _, e ->

        LogUtils.e("progressDialogCoroutineExceptionHandler=====>:${e.message}")
        e.printStackTrace()
        /*隐藏进度窗*/
        dismissProgressDialog()
        val msg =
            if (TextUtils.isEmpty(e.message)) ViewStateContstants.UNKONW_ERROR else e.message!!
        showError(msg)
    }

    fun launchProgressDialog(
        url: String,
        title: String,
        checkNetWork: Boolean = true,
        exceptionHandler: CoroutineExceptionHandler = progressDialogCoroutineExceptionHandler,
        block: suspend CoroutineScope.() -> Unit
    ) {

        viewModelScope.launch(exceptionHandler) {
            if (checkNetWork && !NetworkUtils.isConnected()) {
                showNoNet()
                cancel()
                return@launch
            }

            ProgressManager.getInstance()
                .addResponseListener(url, object : ProgressListener {
                    override fun onProgress(progressInfo: ProgressInfo?) {
//                            LogUtils.i("下载进度:${progressInfo?.percent}")
                        changeProgress(progressInfo!!.percent)
                    }

                    override fun onError(id: Long, e: Exception?) {
                        LogUtils.i("下载出错：${e?.printStackTrace()}")

                        dismissProgressDialog()
                    }
                })

            showProgressDialog(title)
            block()
            dismissProgressDialog()

        }

    }

    private val supervisorExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            LogUtils.e("supervisorExceptionHandler 捕获到异常了：${throwable}")
            throwable.printStackTrace()
        }

    /**
     * 用来请求一个跟页面状态没啥关系同时不需要对异常进行特殊处理的接口
     * @param checkNetWork Boolean
     * @param exceptionHandler CoroutineExceptionHandler
     * @param block [@kotlin.ExtensionFunctionType] SuspendFunction1<CoroutineScope, Unit>
     */
    fun launchHttpWithSupervisor(
        checkNetWork: Boolean = true,
        exceptionHandler: CoroutineExceptionHandler = supervisorExceptionHandler,
        block: suspend CoroutineScope.() -> Unit
    ) {

        viewModelScope.launch(exceptionHandler) {
            if (checkNetWork && !NetworkUtils.isConnected()) {
                showNoNet()
                cancel()
                return@launch
            }
            supervisorScope(block)
        }

    }

    fun launchWithSupervisor(
        exceptionHandler: CoroutineExceptionHandler = supervisorExceptionHandler,
        block: suspend CoroutineScope.() -> Unit
    ) {

        viewModelScope.launch(exceptionHandler) {
            supervisorScope { block() }
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