package com.yzq.lib_base.data

import com.yzq.lib_constants.ViewStateContstants


/*
*
* 视图状态
* */
data class ViewStateBean(
    var state: Int = ViewStateContstants.showLoaddingDialog,
    var message: String = "请求中"
)