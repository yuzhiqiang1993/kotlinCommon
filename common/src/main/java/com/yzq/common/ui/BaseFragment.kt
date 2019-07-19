package com.yzq.common.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yzq.common.eventBus.EventBusUtil
import com.yzq.common.eventBus.EventMsg
import com.yzq.common.mvp.model.CompressImgModel
import com.yzq.common.mvp.view.BaseView
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
abstract class BaseFragment : Fragment(), BaseView {


    private var stateView: StateView? = null
    private var contentLayout: View? = null
    private var isRefreshLayout: Boolean = false

    @Inject
    lateinit var compressImgModel: CompressImgModel


    override fun onAttach(context: Context) {
        super.onAttach(context)

        initArgs(arguments!!)
        initInject()
        initPresenter()

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


    protected open fun initInject() {


    }

    protected open fun initPresenter() {


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


    protected fun preViewImg(path: String) {

        val intent = Intent(activity, ImgPreviewActivity::class.java)
        intent.putExtra(ImgPreviewActivity.IMG_PATH, path)

        startActivity(intent)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBusUtil.unregister(this)
    }

    fun onBackPressed(): Boolean {

        return false

    }

    override fun showLoadding() {
        stateView?.showLoading()
        contentLayout?.visibility = View.GONE

    }

    override fun showContent() {
        stateView?.hide()
        contentLayout?.visibility = View.VISIBLE

        if (isRefreshLayout and (contentLayout != null)) {
            (contentLayout as SwipeRefreshLayout).isRefreshing = false
        }


    }

    override fun showNoData() {
        stateView?.showNoData()
        contentLayout?.visibility = View.GONE

    }

    override fun showNoNet() {
        stateView?.showNoNet()
        contentLayout?.visibility = View.GONE

    }

    override fun showError(msg: String) {
        stateView?.showError(msg)
        contentLayout?.visibility = View.GONE
    }


    protected fun initStateView(stateView: StateView, contentLayout: View, isRefreshLayout: Boolean = false) {
        this.stateView = stateView
        this.contentLayout = contentLayout
        this.isRefreshLayout = isRefreshLayout
    }

}