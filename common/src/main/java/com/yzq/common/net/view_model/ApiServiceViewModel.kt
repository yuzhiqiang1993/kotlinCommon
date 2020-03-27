package com.yzq.common.net.view_model

import com.yzq.common.net.api.ApiServiceModel
import com.yzq.lib_base.view_model.BaseViewModel

open class ApiServiceViewModel : BaseViewModel() {
    protected val apiServiceModel by lazy { ApiServiceModel() }
}