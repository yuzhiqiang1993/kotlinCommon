package com.yzq.lib_base.ui.state_view

import android.text.TextUtils
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.yzq.lib_base.ui.activity.BaseActivity
import com.yzq.lib_base.ui.state_view.constants.ViewStateContstants
import com.yzq.lib_base.ui.state_view.data.ViewStateBean
import com.yzq.lib_materialdialog.*
import com.yzq.lib_widget.state_view.StateView


/**
 * @description: 状态视图管理器
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/5/23
 * @time   : 21:02
 */

class StateViewManager(private val activity: BaseActivity) {

    private val loadingDialog by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { activity.getLoadingDialog() }//加载框
    private val progressDialog by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { activity.getProgressDialog() } //进度框

    val httpFirst = 0//首次请求
    val httpRefresh = 1//刷新
    val httpLoadMore = 2//加载更多

    var requestType = httpFirst

    private var stateView: StateView? = null
    private var contentLayout: View? = null


    fun initStateView(
        stateView: StateView,
        contentLayout: View,
    ) {
        this.stateView = stateView
        this.contentLayout = contentLayout
    }

    fun switchToFirst() {
        this.requestType = httpFirst
    }

    fun switchToLoadMore() {
        this.requestType = httpLoadMore
    }

    fun switchToRefresh() {
        this.requestType = httpRefresh
    }

    /**
     * 处理视图UI变化显示逻辑
     * @param viewStateBean ViewStateBean
     */
    fun handleViewState(viewStateBean: ViewStateBean) {
        val content = viewStateBean.message
        when (viewStateBean.state) {
            ViewStateContstants.showLoadingDialog -> {
                showLoadingDialog(content)
            }
            ViewStateContstants.dismissLoadingDialog -> {
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
            ViewStateContstants.showLoading -> {
                showLoading()
            }
        }

    }


    /**
     * 显示加载框
     *
     * @param message  加载框文本信息
     */
    fun showLoadingDialog(message: String) {
        loadingDialog.setLoadingMessage(message)
        loadingDialog.show()
    }

    /**
     *
     *解除加载框
     */
    fun dismissLoadingDialog() {
        LogUtils.i("dismissLoadingDialog")
        loadingDialog.dismiss()
    }

    /**
     * 显示进度框
     *
     * @param title  提示文本
     */
    fun showProgressDialog(title: String) {
        progressDialog.changeTitle(title)
        progressDialog.show()


    }

    /**
     * 解除进度框
     *
     */
    fun dismissProgressDialog() {
        progressDialog.dismiss()
    }

    /**
     * 修改进度框进度
     *
     * @param percent  当前进度
     */
    fun changeProgress(percent: Int) {
        progressDialog.changeProgress(percent)

    }


    fun showErrorDialog(msg: String?) {
        if (TextUtils.isEmpty(msg)) {
            activity.showBaseDialog(message = ViewStateContstants.UNKONW_ERROR)
        } else {
            activity.showBaseDialog(message = msg!!)
        }

    }


    /**
     * 显示加载中
     *
     */
    fun showLoading() {

        if (requestType == httpFirst) {
            stateView?.showLoading()
            contentLayout?.visibility = View.GONE
        }


    }

    /**
     * 显示内容布局
     *
     */
    fun showContent() {
        stateView?.hide()
        contentLayout?.visibility = View.VISIBLE

        cancelRefresh()
    }


    /*取消下拉刷新动画*/
    private fun cancelRefresh() {


        if (requestType == httpRefresh && (contentLayout != null) && contentLayout is SwipeRefreshLayout) {
            (contentLayout as SwipeRefreshLayout).isRefreshing = false
        }
    }

    /**
     * 显示无数据
     *
     */
    fun showNoData() {

        if (requestType != httpLoadMore) {
            stateView?.showNoData()
            contentLayout?.visibility = View.GONE
            cancelRefresh()
        }


    }

    /**
     * 显示无网络
     *
     */
    fun showNoNet() {

        if (requestType == httpLoadMore) {
            ToastUtils.showLong(ViewStateContstants.NO_NET)

        } else {
            stateView?.showNoNet()
            contentLayout?.visibility = View.GONE
            cancelRefresh()
        }


    }

    /**
     * 显示错误信息
     *
     * @param msg  错误信息
     */
    fun showError(msg: String) {


        if (requestType == httpLoadMore) {
            ToastUtils.showShort(msg)

        } else {

            stateView?.showError(msg)

            contentLayout?.visibility = View.GONE

            cancelRefresh()

        }


    }


}