package com.yzq.kotlincommon.mvp.view

import com.yzq.common.mvp.view.BaseView
import com.yzq.kotlincommon.data.Subject

interface ImgListView : BaseView {
    fun requestSuccess(subjects: List<Subject>)

}