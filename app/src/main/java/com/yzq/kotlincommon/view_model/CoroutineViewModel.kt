package com.yzq.kotlincommon.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yzq.baseui.UIState
import com.yzq.baseui.UiStateViewModel
import com.yzq.coroutine.ext.launchSafety
import com.yzq.data.gaode.Geocoder
import com.yzq.kotlincommon.api.ApiService
import com.yzq.logger.Logger
import com.yzq.net.RetrofitFactory
import com.yzq.net.core.onError
import com.yzq.net.core.onException
import com.yzq.net.core.onSuccess
import com.yzq.net.ext.apiCall
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoroutineViewModel : UiStateViewModel() {

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
            delay(2000)
            val response = apiCall {
                RetrofitFactory.instance.getService(ApiService::class.java).geocoder()
            }

            response.onSuccess {
                _geocoder.value = it
                _uiStateFlow.value = UIState.ShowContent
            }
            response.onError { code, message ->
                Logger.i("onFailed--code:$code,message:$message")
                _uiStateFlow.value = UIState.ShowError(message)
            }

            response.onException {
                Logger.i("onException：$it")
                _uiStateFlow.value = UIState.ShowError(it.localizedMessage ?: "onException")
            }
        }

        viewModelScope.launchSafety {
            delay(2000)
            _geocoderFlow.value =
                RetrofitFactory.instance.getService(ApiService::class.java).geocoder().body()
            _uiStateFlow.value = UIState.ShowContent
        }
    }
}
