package com.yzq.kotlincommon.mvp.presenter

import com.blankj.utilcode.util.ToastUtils
import com.yzq.common.data.BaseResp
import com.yzq.common.extend.transform
import com.yzq.common.mvp.presenter.BasePresenter
import com.yzq.common.rx.BaseObserver
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.mvp.model.NewsModel
import com.yzq.kotlincommon.mvp.view.NewsView
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class NewsPresenter @Inject constructor() : BasePresenter<NewsView>() {

    @Inject
    lateinit var model: NewsModel

    fun requestData() {
        model.getData()
                .transform(lifecycleOwner)
                .subscribe(object : BaseObserver<NewsBean>(view) {
                    override fun onNext(newsBean: NewsBean) {

                        if (newsBean.stat.equals("1")) {
                            view.requestSuccess(newsBean.data)
                        } else {
                            ToastUtils.showLong("数据请求错误")
                        }
                    }
                })
    }

    fun getNews(): Deferred<BaseResp<NewsBean>> {

        return model.getNews()

    }


}