package com.yzq.kotlincommon.mvp.presenter

import com.yzq.common.extend.transform
import com.yzq.common.mvp.presenter.BasePresenter
import com.yzq.common.rx.BaseObserver
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.mvp.model.MainModel
import com.yzq.kotlincommon.mvp.view.MainView
import javax.inject.Inject

class MainPresenter @Inject constructor() : BasePresenter<MainView>() {

    @Inject
    lateinit var model: MainModel

    fun requestData() {


        model.getData().transform(owner).subscribe(object : BaseObserver<NewsBean>(view) {
            override fun onNext(t: NewsBean) {

                view.requestSuccess(t.data)

            }


        })


    }


}