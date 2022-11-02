package com.yzq.kotlincommon.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yzq.base.view_model.BaseViewModel
import com.yzq.common.data.gaode.Geocoder
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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


//        viewModelScope.launch {
//            val response = apiCall {
//                RetrofitFactory.instance.getService(ApiService::class.java).geocoder()
//            }
//
//            response.onSuccess {
//                _geocoderFlow.value = it
//            }
//            response.onError { code, message ->
//                LogUtils.i("onFailed--code:$code,message:$message")
//            }
//
//            response.onException {
//                LogUtils.i("onException：$it")
//            }
//        }


        launchLoading {
            _geocoderFlow.value =
                RetrofitFactory.instance.getService(ApiService::class.java).geocoder().body()
        }
    }
}