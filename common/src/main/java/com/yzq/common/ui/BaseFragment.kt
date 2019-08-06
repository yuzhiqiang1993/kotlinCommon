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
import com.yzq.common.constants.BaseConstants
import com.yzq.common.eventBus.EventBusUtil
import com.yzq.common.eventBus.EventMsg
import com.yzq.common.mvvm.model.CompressImgModel
import com.yzq.common.widget.StateView
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


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

    @Inject
    lateinit var compressImgModel: CompressImgModel


    override fun onAttach(context: Context) {
        super.onAttach(context)

        initArgs(arguments!!)


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getContentLayoutId(), container, false)

        EventBusUtil.register(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWidget()
        initData()

    }


    protected open fun initArgs(arguments: Bundle) {


    }


    /*初始化数据*/
    protected fun initData() {

    }


    /*返回布局id*/
    protected abstract fun getContentLayoutId(): Int


    /*初始化View*/
    protected fun initWidget() {


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
    fun onEventMainThread(msg: EventMsg) {
    }


    override fun onDestroyView() {
        super.onDestroyView()
        EventBusUtil.unregister(this)
    }

    fun onBackPressed(): Boolean {

        return false

    }

    fun showLoadding() {
        stateView?.showLoading()
        contentLayout?.visibility = View.GONE

    }

    fun showContent() {
        stateView?.hide()
        contentLayout?.visibility = View.VISIBLE

        if (isRefreshLayout and (contentLayout != null)) {
            (contentLayout as SwipeRefreshLayout).isRefreshing = false
        }


    }

    fun showNoData() {
        stateView?.showNoData()
        contentLayout?.visibility = View.GONE

    }

    fun showNoNet() {
        stateView?.showNoNet()
        contentLayout?.visibility = View.GONE

    }

    fun showError(msg: String?) {

        if (TextUtils.isEmpty(msg)) {
            stateView?.showError(BaseConstants.UNKONW_ERROR)
        } else {
            stateView?.showError(msg!!)
        }
        contentLayout?.visibility = View.GONE
    }


    fun showLoadingDialog(message: String) {
        (activity as BaseActivity).showLoadingDialog(message)
    }

    fun dismissLoadingDialog() {
        (activity as BaseActivity).dismissLoadingDialog()
    }

    fun showErrorDialog(msg: String?) {
        (activity as BaseActivity).showErrorDialog(msg)
    }


    fun showProgressDialog(title: String) {
        (activity as BaseActivity).showProgressDialog(title)
    }

    fun changeProgress(percent: Int) {
        (activity as BaseActivity).changeProgress(percent)
    }

    fun dismissProgressDialog() {
        (activity as BaseActivity).dismissProgressDialog()
    }

    protected fun initStateView(stateView: StateView, contentLayout: View, isRefreshLayout: Boolean = false) {
        this.stateView = stateView
        this.contentLayout = contentLayout
        this.isRefreshLayout = isRefreshLayout
    }

}