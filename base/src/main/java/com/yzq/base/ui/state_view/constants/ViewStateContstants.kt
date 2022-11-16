package com.yzq.base.ui.state_view.constants

import com.blankj.utilcode.util.StringUtils

/*视图状态常量*/
object ViewStateContstants {
    const val showLoadingDialog = 0
    const val dismissLoadingDialog = 1
    const val showProgressDialog = 2
    const val dismissProgressDialog = 3
    const val changeProgress = 4
    const val showErrorDialog = 5
    const val showNoNet = 6
    const val showError = 7
    const val showLoading = 8

    val NO_NET = StringUtils.getString(com.yzq.resource.R.string.no_net)!!
    val PARSE_DATA_ERROE = StringUtils.getString(com.yzq.resource.R.string.data_parse_error)!!
    val SERVER_TIMEOUT = StringUtils.getString(com.yzq.resource.R.string.timeout)!!
    val LOADING = StringUtils.getString(com.yzq.resource.R.string.loading)!!
    val UNKONW_ERROR = StringUtils.getString(com.yzq.resource.R.string.unknown_exception)!!
}
