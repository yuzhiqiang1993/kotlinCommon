package com.yzq.common.net.view_model

import com.yzq.base.view_model.BaseViewModel
import com.yzq.common.net.api.ApiServiceModel

open class ApiServiceViewModel : BaseViewModel() {
    protected val apiServiceModel by lazy { ApiServiceModel() }
}