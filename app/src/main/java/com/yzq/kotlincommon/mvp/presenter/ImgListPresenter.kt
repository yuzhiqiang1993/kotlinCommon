package com.yzq.kotlincommon.mvp.presenter

import com.yzq.common.extend.transform
import com.yzq.common.mvp.presenter.BasePresenter
import com.yzq.common.rx.BaseSingleObserver
import com.yzq.kotlincommon.data.MovieBean
import com.yzq.kotlincommon.mvp.model.ImgListModel
import com.yzq.kotlincommon.mvp.view.ImgListView
import javax.inject.Inject

class ImgListPresenter @Inject constructor() : BasePresenter<ImgListView>() {

    @Inject
    lateinit var model: ImgListModel

    fun getImgs(start: Int, count: Int) {
        model.getImgs(start, count)
                .transform(lifecycleOwner)
                .subscribe(object : BaseSingleObserver<MovieBean>(view) {
                    override fun onSuccess(movieBean: MovieBean) {
                        view.requestSuccess(movieBean.subjects)
                    }
                })
    }


}