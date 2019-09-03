package com.yzq.common.ui

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yzq.lib_constants.BaseConstants
import com.yzq.lib_widget.StateView
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * @description: fragment基类
 * @author : yzq
 * @date   : 2018/7/11
 * @time   : 9:49
 *
 */
abstract class BaseFragment : Fragment() {


    private var stateView: StateView? = null
    private var contentLayout: View? = null
    private var isRefreshLayout: Boolean = false


    override fun onAttach(context: Context) {
        super.onAttach(context)
        initArgs(arguments)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getContentLayoutId(), container, false)
        com.yzq.lib_eventbus.EventBusUtil.register(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initWidget()
        initData()

    }

    protected open fun initViewModel() {}


    protected open fun initArgs(arguments: Bundle?) {


    }


    /*初始化数据*/
    protected open fun initData() {

    }


    /*返回布局id*/
    protected abstract fun getContentLayoutId(): Int


    /*初始化View*/
    protected open fun initWidget() {


    }


    /**
     * 初始化Fragment中的Toolbar
     *
     * @param toolbar  toolbar
     * @param title  要显示的标题
     * @param displayHome  是否显示左侧的图标（默认不显示）
     */
    protected open fun initToolbar(toolbar: Toolbar, title: String, displayHome: Boolean = false) {
        toolbar.title = title
        (activity as BaseActivity).setSupportActionBar(toolbar)
        (activity as BaseActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(displayHome)

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEventMainThread(msg: com.yzq.lib_eventbus.EventMsg) {
    }


    override fun onDestroyView() {
        super.onDestroyView()
        com.yzq.lib_eventbus.EventBusUtil.unregister(this)
    }

    protected open fun onBackPressed(): Boolean {

        return false

    }

    protected open fun showLoadding() {
        stateView?.showLoading()
        contentLayout?.visibility = View.GONE

    }

    protected open fun showContent() {
        stateView?.hide()
        contentLayout?.visibility = View.VISIBLE

        if (isRefreshLayout and (contentLayout != null)) {
            (contentLayout as SwipeRefreshLayout).isRefreshing = false
        }


    }

    protected open fun showNoData() {
        stateView?.showNoData()
        contentLayout?.visibility = View.GONE

    }

    protected open fun showNoNet() {
        stateView?.showNoNet()
        contentLayout?.visibility = View.GONE

    }

    protected open fun showError(msg: String?) {

        if (TextUtils.isEmpty(msg)) {
            stateView?.showError(com.yzq.lib_constants.BaseConstants.UNKONW_ERROR)
        } else {
            stateView?.showError(msg!!)
        }
        contentLayout?.visibility = View.GONE
    }


    protected open fun showLoadingDialog(message: String) {
        (activity as BaseActivity).showLoadingDialog(message)
    }

    protected open fun dismissLoadingDialog() {
        (activity as BaseActivity).dismissLoadingDialog()
    }

    protected open fun showErrorDialog(msg: String?) {
        (activity as BaseActivity).showErrorDialog(msg)
    }


    protected open fun showProgressDialog(title: String) {
        (activity as BaseActivity).showProgressDialog(title)
    }

    protected open fun changeProgress(percent: Int) {
        (activity as BaseActivity).changeProgress(percent)
    }

    protected open fun dismissProgressDialog() {
        (activity as BaseActivity).dismissProgressDialog()
    }

    protected open fun initStateView(
        stateView: StateView,
        contentLayout: View,
        isRefreshLayout: Boolean = false
    ) {
        this.stateView = stateView
        this.contentLayout = contentLayout
        this.isRefreshLayout = isRefreshLayout
    }

}