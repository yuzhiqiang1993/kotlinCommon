package com.yzq.base.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * @description: fragment基类
 * @author : yzq
 * @date : 2018/7/11
 * @time : 9:49
 *
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected val currentClassTag = "${System.currentTimeMillis()}-${this.javaClass.simpleName}"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initArgs(arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVariable()
        initWidget()
        /*viewmodel的监听*/
        observeViewModel()
        initData()
    }

    protected open fun initViewModel() {}

    protected open fun initVariable() {}

    /**
     * Init args
     *
     * @param arguments
     */
    protected open fun initArgs(arguments: Bundle?) {
    }

    /*初始化数据*/
    protected open fun initData() {
    }

    /*初始化View*/
    protected open fun initWidget() {}

    /*监听vm中的数据*/
    protected open fun observeViewModel() {}

    /*返回键监听*/
    open fun onBackPressed(): Boolean {
        return false
    }
}
