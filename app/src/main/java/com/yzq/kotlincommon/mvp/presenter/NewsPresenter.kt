package com.yzq.kotlincommon.mvp.presenter

import com.blankj.utilcode.util.ToastUtils
import com.yzq.common.extend.tranform
import com.yzq.common.mvp.presenter.BasePresenter
import com.yzq.common.rx.BaseSingleObserver
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.mvp.model.NewsModel
import com.yzq.kotlincommon.mvp.view.NewsView
import javax.inject.Inject

class NewsPresenter @Inject constructor() : BasePresenter<NewsView>() {

    @Inject
    lateinit var model: NewsModel

    fun requestData() {
        model.getData()
                .tranform(lifecycleOwner)
                .subscribe(object : BaseSingleObserver<NewsBean>(view) {
                    override fun onSuccess(newsBean: NewsBean) {

                        if (newsBean.stat.equals("1")) {
                            view.requestSuccess(newsBean.data)
                        } else {
                            ToastUtils.showLong("数据请求错误")
                        }
                    }

                })
    }


}