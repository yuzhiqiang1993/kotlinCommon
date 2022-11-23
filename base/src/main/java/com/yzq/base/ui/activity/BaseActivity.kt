package com.yzq.base.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.drake.statelayout.StateLayout
import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.base.view_model.BaseViewModel
import com.yzq.base.view_model.UIState
import com.yzq.coroutine.runMain
import com.yzq.materialdialog.getLoadingDialog
import com.yzq.materialdialog.setLoadingMessage
import com.yzq.materialdialog.showBaseDialog

/**
 * @Description: Activity基类
 * @author : yzq
 * @date : 2018/7/2
 * @time : 13:55
 *
 */

abstract class BaseActivity : AppCompatActivity {

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    protected val loadingDialog by lazy { getLoadingDialog() }

    private var stateLayout: StateLayout? = null

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

        /*参数初始化，intent携带的值*/
        initArgs(intent.extras)
        /*变量初始化*/
        initVariable()
        /*控件的初始化，例如绑定点击时间之类的*/
        initWidget()
        /*一些监听的初始化，*/
        initListener()
        /*viewmodel的监听*/
        observeViewModel()
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

    protected open fun observeViewModel() {}

    /**
     * 初始化数据
     *
     */
    protected open fun initData() {
    }

    /**
     * Toolbar的返回按钮
     *
     */
    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return super.onSupportNavigateUp()
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

    protected fun observeUIState(vm: BaseViewModel, stateLayout: StateLayout? = null) {
        this.stateLayout = stateLayout
        vm.uiState.observe(this) {
            when (it) {
                is UIState.DissmissLoadingDialog -> {
                    runMain { loadingDialog.dismiss() }
                }
                is UIState.ShowLoadingDialog -> {
                    runMain { loadingDialog.setLoadingMessage(it.msg).show() }
                }
                is UIState.ShowToast -> {
                    runMain { ToastUtils.showLong(it.msg) }
                }
                is UIState.ShowDialog -> {
                    runMain { showBaseDialog(message = it.msg) }
                }
                is UIState.ShowContent -> {
                    stateLayout?.run { runMain { showContent() } }
                }
                is UIState.ShowError -> {
                    stateLayout?.run { runMain { showError(it.msg) } }
                }
                is UIState.ShowLoading -> {
                    stateLayout?.run { runMain { showLoading(it.msg) } }
                }
                is UIState.ShowEmpty -> {
                    stateLayout?.run { runMain { showEmpty() } }
                }
            }
        }
    }
}
