package com.yzq.common.data

import com.yzq.common.constants.ViewStateContstants

/*
*
* 视图状态
* */
data class ViewStateBean(var state: Int = ViewStateContstants.showLoaddingDialog, var message: String = "请求中")