package com.yzq.kotlincommon.mvp.view

import com.yzq.common.mvp.view.BaseView
import com.yzq.kotlincommon.data.BaiDuImgBean

interface ImgListView : BaseView {
    fun requestSuccess(baiDuImgBean: BaiDuImgBean)

}