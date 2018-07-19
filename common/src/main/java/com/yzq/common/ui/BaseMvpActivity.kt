package com.yzq.common.ui

import com.blankj.utilcode.util.LogUtils
import com.yzq.common.mvp.presenter.BasePresenter
import com.yzq.common.mvp.presenter.CompressImgPresenter
import com.yzq.common.mvp.view.BaseView
import com.yzq.common.mvp.view.CompressImgView
import javax.inject.Inject


/**
 * @description: mvp Activity基类
 * @author : yzq
 * @date   : 2018/7/12
 * @time   : 10:40
 *
 */

abstract class BaseMvpActivity<V : BaseView, P : BasePresenter<V>> : BaseActivity(), BaseView, CompressImgView {


    @Inject
    lateinit var presenter: P

    @Inject
    lateinit var compressImgPresenter: CompressImgPresenter

    abstract override fun initInject()

    override fun initPresenter() {

        presenter.initPresenter(this as V, this)

    }


    protected fun initCompressImgPresenter(){
        compressImgPresenter.initPresenter(this,this)
    }


    override fun compressImgSuccess(path: String) {
        LogUtils.i("压缩后图片路径：" + path)
    }


}
