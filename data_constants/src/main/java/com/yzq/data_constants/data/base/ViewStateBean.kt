package com.yzq.data_constants.data.base

import com.yzq.data_constants.constants.ViewStateContstants

/*
*
* 视图状态
* */
data class ViewStateBean(var state: Int = ViewStateContstants.showLoaddingDialog, var message: String = "请求中")