package com.yzq.baseui

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
abstract class BaseFragment(@LayoutRes private val contentLayoutId: Int = 0) :
    Fragment(contentLayoutId) {


    protected val TAG = "${this.javaClass.simpleName}-${this.hashCode()}"


    override fun onAttach(context: Context) {
        super.onAttach(context)
        initArgs(arguments)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //初始化数据
        initVariable()
        //初始化view
        initWidget()
        //viewmodel的监听
        observeViewModel()
        initData()
    }


    protected open fun initVariable() {}

    /**
     * Init args
     *
     * @param arguments
     */
    protected open fun initArgs(arguments: Bundle?) {
    }

    /**
     * 初始化数据
     */
    protected open fun initData() {
    }

    /**
     * 初始化View
     */
    protected open fun initWidget() {}

    /**
     * 监听vm中的数据
     */
    protected open fun observeViewModel() {}

    /**
     * 返回键监听
     * @return Boolean
     */
    open fun onBackPressed(): Boolean {/*如果子类重写返回true  表示fragment要拦截返回键的逻辑 自己做处理*/
        return false
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
