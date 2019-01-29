package com.yzq.common.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.yzq.common.base.mvp.model.CompressImgModel
import com.yzq.common.eventBus.EventBusUtil
import com.yzq.common.eventBus.EventMsg
import com.yzq.common.mvp.view.BaseView
import com.yzq.common.widget.Dialog
import com.yzq.common.widget.StateView
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


/**
 * @Description: Activity基类
 * @author : yzq
 * @date   : 2018/7/2
 * @time   : 13:55
 *
 */

abstract class BaseActivity : AppCompatActivity(), BaseView {


    private var lastClickTime: Long = 0
    private val intervalTime = 300
    private var allowFastClick = false

    private var loaddingDialog: MaterialDialog? = null
    private var progressDialog: MaterialDialog? = null

    private var stateView: StateView? = null
    private var contentLayout: View? = null
    private var isRefreshLayout: Boolean = false
    private var showBackHint = false

    @Inject
    lateinit var compressImgModel: CompressImgModel


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


        initInject()
        initPresenter()

        Dialog.initDialog(this)
        EventBusUtil.register(this)

        initArgs(intent.extras)
        setContentView(getContentLayoutId())

        initWidget()
        initData()

    }


    protected open fun initArgs(extras: Bundle?) {

    }


    protected abstract fun getContentLayoutId(): Int


    protected open fun initInject() {

    }

    protected open fun initPresenter() {


    }


    protected open fun initWidget() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEventMainThread(eventMsg: EventMsg) {


    }

    protected open fun initData() {}


    protected open fun initToolbar(toolbar: Toolbar, title: String, showBackHint: Boolean = false) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        this.showBackHint = showBackHint
    }


    @SuppressLint("AutoDispose")
    protected fun initHeader(backIv: AppCompatImageView, titleTv: TextView, title: String, showBackHint: Boolean = false) {
        titleTv.text = title

        backIv.setOnClickListener {
            if (showBackHint) {
                Dialog.showBackHintDialog().subscribe { finish() }
            } else {
                finish()
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return super.onSupportNavigateUp()
    }


    protected open fun setAllowFastClick(allowFastClick: Boolean) {
        this.allowFastClick = allowFastClick
    }


    protected fun preViewImg(name: String, path: String) {

        val intent = Intent(this, ImgPreviewActivity::class.java)
        intent.putExtra(ImgPreviewActivity.IMG_NAME, name)
        intent.putExtra(ImgPreviewActivity.IMG_PATH, path)


        startActivity(intent)

    }


    /*防止双击*/
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


    @SuppressLint("AutoDispose", "CheckResult")
    override fun onBackPressed() {

        if (showBackHint) {
            Dialog.showBackHintDialog().subscribe { finish() }
        } else {
            finish()
        }

    }


    override fun onResume() {
        super.onResume()
        Dialog.initDialog(this)

    }


    override fun onDestroy() {
        super.onDestroy()

        EventBusUtil.unregister(this)
    }


    override fun showLoadingDialog(content: String) {

        if (loaddingDialog == null) {
            loaddingDialog = Dialog.getLoaddingDialog()
        }

        loaddingDialog!!.setContent(content)
        loaddingDialog!!.show()


    }

    override fun dismissLoadingDialog() {
        loaddingDialog!!.dismiss()
    }

    override fun showProgressDialog(title: String, content: String) {

        if (progressDialog == null) {
            progressDialog = Dialog.getProgressDialog(title, content)
        }

        progressDialog!!.show()


    }

    override fun dismissProgressDialog() {

        progressDialog!!.dismiss()
    }

    override fun changeProgress(percent: Int) {
        progressDialog!!.setProgress(percent)

    }

    override fun showErrorDialog(msg: String) {
        Dialog.showBase(content = msg)
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

