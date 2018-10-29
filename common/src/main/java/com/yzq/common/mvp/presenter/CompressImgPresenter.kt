package com.yzq.common.mvp.presenter

import com.yzq.common.base.mvp.model.CompressImgModel
import com.yzq.common.extend.transform
import com.yzq.common.mvp.view.CompressImgView
import javax.inject.Inject


/**
 * @description: 图片压缩Presenter
 * @author : yzq
 * @date   : 2018/7/19
 * @time   : 16:23
 *
 */

class CompressImgPresenter @Inject constructor() : BasePresenter<CompressImgView>() {


    @Inject
    lateinit var model: CompressImgModel


    /*压缩图片,默认不添加水印*/
    fun compressImg(path: String) {
        model.compressImg(path).transform(lifecycleOwner).subscribe {
            view.compressImgSuccess(it)
        }
    }

    /*压缩图片并添加水印*/
    fun compressImgWithWatermark(path: String) {
        model.compressImgWithWatermark(path).transform(lifecycleOwner).subscribe { view.compressImgSuccess(it) }
    }

}