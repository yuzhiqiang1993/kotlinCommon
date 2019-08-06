package com.yzq.common.data

import com.yzq.common.constants.ViewStateContstants

data class ViewStateBean(var state: Int = ViewStateContstants.showLoaddingDialog, var message: String = "请求中")