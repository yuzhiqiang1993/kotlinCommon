package com.yzq.common.ui

import com.yzq.common.mvp.presenter.BasePresenter
import com.yzq.common.mvp.view.BaseView
import javax.inject.Inject


/**
 * @description: mvpFragment基类
 * @author : yzq
 * @date   : 2018/7/11
 * @time   : 10:36
 *
 */

abstract class BaseMvpFragment<V : BaseView, P : BasePresenter<V>> : BaseFragment() {


    @Inject
    lateinit var presenter: P

    override fun initPresenter() {
        presenter.initPresenter(this as V, this)
    }

}