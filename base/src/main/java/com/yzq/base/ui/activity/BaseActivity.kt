package com.yzq.base.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.blankj.utilcode.util.BarUtils
import com.yzq.base.ui.ImgPreviewActivity
import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.base.ui.state_view.StateViewManager
import com.yzq.eventbus.EventBusUtil
import com.yzq.eventbus.EventMsg
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Description: Activity基类
 * @author : yzq
 * @date   : 2018/7/2
 * @time   : 13:55
 *
 */

abstract class BaseActivity : AppCompatActivity {

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)


    private var lastClickTime: Long = 0//最后一次点击的时间

    private val intervalTime = 300//两次点击之间的间隔
    private var allowFastClick = false//是否允许快速点击

    /*视图状态管理器*/
    protected val stateViewManager by lazy { StateViewManager(this) }

    protected val currentClassTag = "${System.currentTimeMillis()}-${this.javaClass.simpleName}"

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

        /*参数初始化，intent携带的值*/
        initArgs(intent.extras)
        /*viewmodel的初始化*/
        initViewModel()
        /*变量初始化*/
        initVariable()
        /*控件的初始化，例如绑定点击时间之类的*/
        initWidget()
        /*一些监听的初始化，*/
        initListener()
        /*初始化数据*/
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

    /*初始化变量*/
    protected open fun initVariable() {

    }

    /**
     * 初始化控件
     *
     */
    protected open fun initWidget() {
    }

    protected open fun initListener() {

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

        WindowCompat.setDecorFitsSystemWindows(window, false)
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHome)

        if (transparentStatusBar) {
            transStatusBar(toolbar)
        }
    }

    protected open fun transStatusBar(view: View, isLightMode: Boolean = false) {
        BarUtils.transparentStatusBar(this)
        BarUtils.addMarginTopEqualStatusBarHeight(view)
        BarUtils.setStatusBarLightMode(this, isLightMode)
    }

    protected open fun colorStatusBar(
        @ColorRes color: Int = com.yzq.resource.R.color.colorOnPrimary,
        view: View,
        isLightMode: Boolean = false
    ) {
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
                getString(com.yzq.resource.R.string.img_transition)
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

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {

            if (it is BaseFragment) {
                if (it.onBackPressed()) {
                    return
                }
            }
        }
        super.onBackPressed()

    }


    override fun onDestroy() {
        super.onDestroy()
        EventBusUtil.unregister(this)
    }


}

