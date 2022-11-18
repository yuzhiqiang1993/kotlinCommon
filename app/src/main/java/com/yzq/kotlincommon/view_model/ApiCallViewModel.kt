package com.yzq.kotlincommon.view_model

import com.blankj.utilcode.util.LogUtils
import com.yzq.base.view_model.BaseViewModel
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService

/**
 * @description 接口请求的vm
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/2
 * @time 15:52
 */

class ApiCallViewModel : BaseViewModel() {

    private val onException = { t: Throwable -> LogUtils.i("异常了") }

    fun requestData() {
        launchLoadingDialog(onException = onException) {
            val movieBean = RetrofitFactory.instance.getService(ApiService::class.java).userInfo()
            LogUtils.i("movieBean:$movieBean")
        }
    }
}
