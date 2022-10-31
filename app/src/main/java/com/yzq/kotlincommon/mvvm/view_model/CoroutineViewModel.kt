package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.yzq.base.view_model.BaseViewModel
import com.yzq.common.data.api.onException
import com.yzq.common.data.api.onFailed
import com.yzq.common.data.api.onSuccess
import com.yzq.common.data.gaode.Geocoder
import com.yzq.common.ext.apiCall
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoroutineViewModel : BaseViewModel() {

    private val _geocoder by lazy { MutableLiveData<Geocoder>() }

    val geocoder: LiveData<Geocoder> = _geocoder

    /**
     * 内部使用可变的MutableStateFlow 私有 只能在内部更改数据
     */
    private val _geocoderFlow by lazy { MutableStateFlow<Geocoder?>(null) }

    /**
     * 对外暴露不可变的StateFlow
     * 达到外部只能监听数据 不能修改数据的目的
     */
    val geocoderFlow = _geocoderFlow.asStateFlow()

    /*请求数据*/
    fun requestData() {


        viewModelScope.launch {
            val response = apiCall {
                RetrofitFactory.instance.getService(ApiService::class.java).geocoder()
            }

            response.onSuccess {
                _geocoderFlow.value = it
            }
            response.onFailed { code, message ->
                LogUtils.i("onFailed--code:$code,message:$message")
            }

            response.onException {
                LogUtils.i("onException：$it")
            }
        }


//        launchLoading {
//            val response = apiCallWithResponse {
//                RetrofitFactory.instance.getService(ApiService::class.java).geocoder()
//            }
//
//            response.onSuccess {
//                _geocoderFlow.value = it
//            }
//            response.onFailed { code, message ->
//                LogUtils.i("onFailed--code:$code,message:$message")
//            }
//
//            response.onException {
//                LogUtils.i("onException：$it")
//            }
//
//
//        }
    }
}