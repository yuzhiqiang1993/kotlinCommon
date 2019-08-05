package com.yzq.common.data

import com.yzq.common.constants.ViewStateContstants

data class ViewStateBean(var state: Int = ViewStateContstants.showLoadding, var message: String = "请求中")