package com.yzq.common.ui

import android.os.Bundle
import com.yzq.common.mvp.presenter.BasePresenter
import com.yzq.common.mvp.view.BaseView
import javax.inject.Inject


/**
 * @description: mvp Activity基类
 * @author : yzq
 * @date   : 2018/7/12
 * @time   : 10:40
 *
 */


abstract class BaseMvpActivity<V : BaseView, P : BasePresenter<V>> : BaseActivity(), BaseView {


    @Inject
    lateinit var presenter: P


    abstract override fun initInject()


    override fun initPresenter() {
        @Suppress("UNCHECKED_CAST")
        presenter.initPresenter(this as V, this)

    }


}
