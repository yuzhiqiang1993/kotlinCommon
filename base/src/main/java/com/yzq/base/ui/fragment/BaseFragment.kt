package com.yzq.base.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.base.ui.state_view.StateViewManager
import com.yzq.eventbus.EventBusUtil
import com.yzq.eventbus.EventMsg
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * @description: fragment基类
 * @author : yzq
 * @date   : 2018/7/11
 * @time   : 9:49
 *
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {


    protected val stateViewManager by lazy { StateViewManager(activity = requireActivity() as BaseActivity<*>) }

    protected val currentClassTag = "${System.currentTimeMillis()}-${this.javaClass.simpleName}"
    protected var extrasTag = ""


    override fun onAttach(context: Context) {
        super.onAttach(context)
        initArgs(arguments)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
        EventBusUtil.register(this)
        initViewModel()
        initVariable()
        initWidget()
        initData()

    }

    protected open fun initViewModel() {}

    protected open fun initVariable() {}


    protected open fun initArgs(arguments: Bundle?) {


    }


    protected abstract fun initBinding(view: View)

    /*初始化数据*/
    protected open fun initData() {

    }


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
        (activity as BaseActivity<*>).setSupportActionBar(toolbar)
        (activity as BaseActivity<*>).supportActionBar!!.setDisplayHomeAsUpEnabled(displayHome)

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEventMainThread(msg: EventMsg) {
    }


    override fun onDestroyView() {
        super.onDestroyView()
        EventBusUtil.unregister(this)
    }

    /*返回键监听*/
    open fun onBackPressed(): Boolean {
        return false
    }
}