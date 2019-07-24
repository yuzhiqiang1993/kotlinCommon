package com.yzq.kotlincommon.mvp.presenter

import com.yzq.common.extend.transform
import com.yzq.common.mvp.presenter.BasePresenter
import com.yzq.common.rx.BaseSingleObserver
import com.yzq.kotlincommon.data.BaiDuImgBean
import com.yzq.kotlincommon.mvp.model.ImgListModel
import com.yzq.kotlincommon.mvp.view.ImgListView
import javax.inject.Inject

class ImgListPresenter @Inject constructor() : BasePresenter<ImgListView>() {

    @Inject
    lateinit var model: ImgListModel
    
    fun getImgs(currentPage: Int, pageSize: Int) {


        model.getImgs(currentPage, pageSize)
                .transform(lifecycleOwner)
                .subscribe(object : BaseSingleObserver<BaiDuImgBean>(view) {
                    override fun onSuccess(baiDuImgBean: BaiDuImgBean) {

                        view.requestSuccess(baiDuImgBean)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.showError(e.message)
                    }
                })
    }


}