package com.yzq.kotlincommon.mvp.presenter

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
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


}