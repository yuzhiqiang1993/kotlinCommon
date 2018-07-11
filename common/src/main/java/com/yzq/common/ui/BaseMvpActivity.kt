package com.yzq.common.ui

import com.yzq.common.mvp.presenter.BasePresenter
import com.yzq.common.mvp.view.BaseView
import javax.inject.Inject

abstract class BaseMvpActivity<V : BaseView, P : BasePresenter<V>> : BaseActivity(), BaseView {


    @Inject
    lateinit var presenter: P


    abstract override fun initInject()

    override fun initPresenter() {
        presenter.initPresenter(this as V, this)

    }


}
