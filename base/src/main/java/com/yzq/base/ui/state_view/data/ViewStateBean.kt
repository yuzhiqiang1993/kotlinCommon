package com.yzq.base.ui.state_view.data

import com.yzq.base.ui.state_view.constants.ViewStateContstants

/*
*
* 视图状态实体类
* */
data class ViewStateBean(
    var state: Int = ViewStateContstants.showLoadingDialog,
    var message: String = "请求中"
)
