package com.yzq.lib_base.view_model

import android.text.TextUtils
import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.yzq.lib_base.ui.state_view.constants.ViewStateContstants
import com.yzq.lib_base.ui.state_view.data.ViewStateBean
import kotlinx.coroutines.*
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo


/*
* 封装的BaseViewModel
* */
abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    lateinit var lifecycleOwner: LifecycleOwner


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
            LogUtils.i("supervisorExceptionHandler 捕获到异常了：${throwable}")
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

    fun showProgressDialog(title: String) {

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