package com.yzq.common.mvp.presenter

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import com.yzq.common.mvp.view.BaseView


/**
 * @description: presenter基类
 * @author : yzq
 * @date   : 2018/7/10
 * @time   : 13:25
 *
 */

abstract class BasePresenter<V : BaseView>  {

    lateinit var view: V

    lateinit var lifecycleOwner: LifecycleOwner

    fun initPresenter(view: V, owner: LifecycleOwner) {
        this.view = view
        this.lifecycleOwner = owner
    }



}