package com.yzq.kotlincommon.mvp.view

import com.yzq.common.mvp.view.BaseView
import com.yzq.kotlincommon.data.NewsBean

interface NewsView : BaseView {
    fun requestSuccess(data: List<NewsBean.Data>)


}