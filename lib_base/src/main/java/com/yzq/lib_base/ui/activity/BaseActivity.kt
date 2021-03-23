package com.yzq.lib_base.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.yzq.lib_base.R
import com.yzq.lib_base.constants.ViewStateContstants
import com.yzq.lib_base.ui.ImgPreviewActivity
import com.yzq.lib_eventbus.EventBusUtil
import com.yzq.lib_eventbus.EventMsg
import com.yzq.lib_materialdialog.*
import com.yzq.lib_widget.StateView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * @Description: Activity基类
 * @author : yzq
 * @date   : 2018/7/2
 * @time   : 13:55
 *
 */

abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private var lastClickTime: Long = 0//最后一次点击的时间

    private val intervalTime = 300//两次点击之间的间隔
    private var allowFastClick = false//是否允许快速点击

    private val loadingDialog by lazy { getLoadingDialog() }//加载框
    private val progressDialog by lazy { getProgressDialog() } //进度框

    private var stateView: StateView? = null //状态布局
    private var contentLayout: View? = null//内容布局
    private var isRefreshLayout: Boolean = false//是否有下拉刷新
    private var showBackHint = false //是否显示返回提示框
    protected val currentClassTag = "${System.currentTimeMillis()}-${this.javaClass.simpleName}"
    protected var extrasTag = ""

    protected val httpFirst = 0//首次请求
    protected val httpRefresh = 1//刷新
    protected val httpLoadMore = 2//加载更多

    protected var requestType = httpFirst

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*防止首次安装点击home键重新实例化*/
        if (!this.isTaskRoot) {
            if (intent != null) {
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == intent.action) {
                    finish()
                    return
                }
            }
        }

        EventBusUtil.register(this)

        initArgs(intent.extras)

        initContentView()
        initViewModel()
        initWidget()

        initData()


    }


    /**
     * 初始化参数
     *
     * @param extras  传递的参数对象
     */
    protected open fun initArgs(extras: Bundle?) {

    }


    protected open fun initViewModel() {}


    /*
    * 初始化视图
    * */
    protected abstract fun initContentView()

    /**
     * 初始化控件
     *
     */
    protected open fun initWidget() {
    }

    /**
     * 处理Eventbus
     *
     * @param eventMsg  传递的消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEventMainThread(eventMsg: EventMsg) {


    }

    /**
     * 初始化数据
     *
     */
    protected open fun initData() {


    }

    /**
     * 初始化Toolbar
     * @param toolbar Toolbar
     * @param title String  标题
     * @param displayHome Boolean  是否显示左边图标，默认显示
     * @param showBackHint Boolean  点击返回键时是否弹窗提示
     * @param transparentStatusBar Boolean 是否沉浸式状态栏，默认状态栏透明
     */
    protected open fun initToolbar(
            toolbar: Toolbar,
            title: String,
            displayHome: Boolean = true,
            showBackHint: Boolean = false,
            transparentStatusBar: Boolean = true
    ) {

        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHome)
        this.showBackHint = showBackHint

        if (transparentStatusBar) {
            transStatusBar(toolbar)

        }
    }


    protected open fun transStatusBar(view: View, isLightMode: Boolean = false) {
        BarUtils.transparentStatusBar(this)
        BarUtils.addMarginTopEqualStatusBarHeight(view)
        BarUtils.setStatusBarLightMode(this, isLightMode)

    }

    protected open fun colorStatusBar(@ColorRes color: Int = R.color.colorOnPrimary, view: View, isLightMode: Boolean = false) {
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, color))
        BarUtils.addMarginTopEqualStatusBarHeight(view)
        BarUtils.setStatusBarLightMode(this, isLightMode)
    }


    /**
     * Toolbar的返回按钮
     *
     */
    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return super.onSupportNavigateUp()
    }


    /**
     * 初始化Header
     *
     * @param backIv  返回图片控件
     * @param titleTv  标题控件
     * @param title  要显示的标题文本
     * @param showBackHint  点击返回时是否显示返回提示框，默认不显示
     */
    protected fun initHeader(
            backIv: AppCompatImageView,
            titleTv: TextView,
            title: String,
            showBackHint: Boolean = false
    ) {
        titleTv.text = title
        this.showBackHint = showBackHint

        backIv.setOnClickListener {
            onBackPressed()
        }

    }

    /**
     * 允许快速点击
     */
    protected open fun allowFastClick() {
        this.allowFastClick = true
    }


    /**
     *
     * @param path String  图片路径
     * @param view View  view
     */
    protected fun preViewImg(path: String, view: View) {
        val intent = Intent(this, ImgPreviewActivity::class.java)
        intent.putExtra(ImgPreviewActivity.IMG_PATH, path)

        val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        view,
                        getString(R.string.img_transition)
                )
        startActivity(intent, options.toBundle())

    }


    /**
     * 事件分发
     * @param ev
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {


        if (!allowFastClick) {
            if (ev?.action == MotionEvent.ACTION_DOWN) {
                if (System.currentTimeMillis() - lastClickTime < intervalTime) {
                    return true
                } else {
                    lastClickTime = System.currentTimeMillis()
                }
            }
        }


        return super.dispatchTouchEvent(ev)
    }


    @SuppressLint("CheckResult", "AutoDispose")
    override fun onBackPressed() {

        if (showBackHint) {
            showBackHintDialog(positiveCallback = {
                finish()
            })
        } else {
            finish()
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
            showBaseDialog(message = ViewStateContstants.UNKONW_ERROR)
        } else {
            showBaseDialog(message = msg!!)
        }

    }

    /**
     * 显示加载中
     *
     */
    fun showLoading() {
        stateView?.showLoading()
        contentLayout?.visibility = View.GONE

    }

    /**
     * 显示内容布局
     *
     */
    fun showContent() {
        stateView?.hide()
        contentLayout?.visibility = View.VISIBLE

        if (isRefreshLayout and (contentLayout != null)) {
            (contentLayout as SwipeRefreshLayout).isRefreshing = false
        }
    }

    /**
     * 显示无数据
     *
     */
    fun showNoData() {
        stateView?.showNoData()
        contentLayout?.visibility = View.GONE


    }

    /**
     * 显示无网络
     *
     */
    fun showNoNet() {

        when (requestType) {
            httpFirst -> {
                stateView?.showNoNet()
                contentLayout?.visibility = View.GONE
            }
            httpRefresh -> {
                (contentLayout as SwipeRefreshLayout).isRefreshing = false
            }
        }


        ToastUtils.showLong(ViewStateContstants.NO_NET)

    }

    /**
     * 显示错误信息
     *
     * @param msg  错误信息
     */
    fun showError(msg: String?) {

        if (TextUtils.isEmpty(msg)) {
            stateView?.showError(ViewStateContstants.UNKONW_ERROR)
        } else {
            stateView?.showError(msg!!)
        }
        contentLayout?.visibility = View.GONE

    }

    /**
     * 初始化状态布局
     *
     * @param stateView  状态控件
     * @param contentLayout  内容布局
     * @param isRefreshLayout  是否是下拉刷新
     */
    protected fun initStateView(
            stateView: StateView,
            contentLayout: View,
            isRefreshLayout: Boolean = false
    ) {
        this.stateView = stateView
        this.contentLayout = contentLayout
        this.isRefreshLayout = isRefreshLayout
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBusUtil.unregister(this)
        cancel()
    }


}
