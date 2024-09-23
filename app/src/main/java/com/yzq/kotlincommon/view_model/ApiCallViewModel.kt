package com.yzq.kotlincommon.view_model

import androidx.lifecycle.viewModelScope
import com.yzq.base.view_model.BaseViewModel
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.coroutine.ext.launchSafety
import com.yzq.logger.Logger
import kotlinx.coroutines.delay

/**
 * @description 接口请求的vm
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/2
 * @time 15:52
 */

class ApiCallViewModel : BaseViewModel() {


    fun requestData() {

        viewModelScope.launchSafety {
            delay(1000)
            val movieBean = RetrofitFactory.instance.getService(ApiService::class.java).userInfo()
            Logger.i("movieBean:$movieBean")
        }
    }
}
