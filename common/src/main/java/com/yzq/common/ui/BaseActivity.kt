package com.yzq.common.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MotionEvent
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.EventBus.EventBusUtil
import com.yzq.common.EventBus.EventMsg
import com.yzq.common.mvp.view.BaseView
import com.yzq.common.widget.Dialog
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


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

        LogUtils.i("initWidget")


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEventMainThread(eventMsg: EventMsg) {


    }

    protected open fun initData() {}


    protected open fun initToolbar(toolbar: Toolbar, title: String) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    override fun onSupportNavigateUp(): Boolean {

        finish()

        return super.onSupportNavigateUp()
    }


    protected open fun setAllowFastClick(allowFastClick: Boolean) {
        this.allowFastClick = allowFastClick
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

//
//    override fun onBackPressed() {
//        var fragments = supportFragmentManager.fragments
//        if (fragments != null && fragments.size > 0) {
//            for (fragment in fragments) {
//                if (fragment is BaseFragment) {
//                    if (fragment.onBackPressed()) {
//                        return
//                    }
//                }
//            }
//        }
//
//        super.onBackPressed()
//    }


    override fun onStart() {
        super.onStart()

        EventBusUtil.register(this)
    }

    override fun onRestart() {
        super.onRestart()
    }


    override fun onResume() {
        super.onResume()
        Dialog.initDialog(this)

    }

    override fun onPause() {
        super.onPause()
    }


    override fun onStop() {
        super.onStop()
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


    }

    override fun showContent() {

    }

    override fun showNoData() {

    }

    override fun showNoNet() {

    }

    override fun showError(msg: String) {

    }
}

