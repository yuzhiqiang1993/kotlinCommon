package com.yzq.kotlincommon.mvp.presenter

import com.yzq.common.extend.transform
import com.yzq.common.mvp.presenter.BasePresenter
import com.yzq.common.rx.BaseSingleObserver
import com.yzq.kotlincommon.data.MovieBean
import com.yzq.kotlincommon.mvp.model.NewsModel
import com.yzq.kotlincommon.mvp.view.MovieView
import javax.inject.Inject

class MoviePresenter @Inject constructor() : BasePresenter<MovieView>() {

    @Inject
    lateinit var model: NewsModel


    fun requestData(start: Int, count: Int) {
        model.getData(start, count)
                .transform(lifecycleOwner)
                .subscribe(object : BaseSingleObserver<MovieBean>(view) {
                    override fun onSuccess(movieBean: MovieBean) {


                        view.requestSuccess(movieBean.subjects)
                    }

                })
    }


}