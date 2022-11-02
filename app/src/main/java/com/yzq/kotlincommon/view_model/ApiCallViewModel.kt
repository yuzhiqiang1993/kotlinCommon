package com.yzq.kotlincommon.view_model

import com.blankj.utilcode.util.LogUtils
import com.yzq.common.net.view_model.ApiServiceViewModel


/**
 * @description 接口请求的vm
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/11/2
 * @time    15:52
 */

class ApiCallViewModel : ApiServiceViewModel() {


    private val onException = { t: Throwable -> LogUtils.i("异常了") }

    fun requestData() {
        launchLoadingDialog(onException = onException) {
            val movieBean = apiServiceModel.getData(1, 10)
            LogUtils.i("movieBean:$movieBean")
        }
    }
}