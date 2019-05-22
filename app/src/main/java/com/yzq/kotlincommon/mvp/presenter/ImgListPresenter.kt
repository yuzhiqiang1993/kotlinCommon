package com.yzq.kotlincommon.mvp.presenter

import com.yzq.common.extend.transform
import com.yzq.common.mvp.presenter.BasePresenter
import com.yzq.common.rx.BaseObserver
import com.yzq.kotlincommon.data.BaiDuImgBean
import com.yzq.kotlincommon.mvp.model.ImgListModel
import com.yzq.kotlincommon.mvp.view.ImgListView
import javax.inject.Inject

class ImgListPresenter @Inject constructor() : BasePresenter<ImgListView>() {

    @Inject
    lateinit var model: ImgListModel

    fun getImgs() {


        model.getImgs()
                .transform(lifecycleOwner)
                .subscribe(object : BaseObserver<BaiDuImgBean>(view) {
                    override fun onNext(baiDuImgBean: BaiDuImgBean) {
                        view.requestSuccess(baiDuImgBean.data as ArrayList<BaiDuImgBean.Data>)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.showError(e.localizedMessage)
                    }
                })
    }


}