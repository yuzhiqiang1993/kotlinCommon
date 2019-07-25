package com.yzq.kotlincommon.mvp.view

import com.yzq.common.mvp.view.BaseView
import com.yzq.kotlincommon.data.Subject

interface MovieView : BaseView {
    fun requestSuccess(data: List<Subject>)


}