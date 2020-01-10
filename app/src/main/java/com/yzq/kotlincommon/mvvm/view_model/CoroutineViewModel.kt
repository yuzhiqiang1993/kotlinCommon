package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.yzq.common.data.gaode.Geocoder
import com.yzq.common.extend.dataConvert
import com.yzq.kotlincommon.net.ApiService
import com.yzq.lib_base.view_model.BaseViewModel
import com.yzq.common.net.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class CoroutineViewModel : BaseViewModel() {


    val geocoder by lazy { MutableLiveData<Geocoder>() }


    /*请求数据*/
    fun requestData() {

        launchLoading {

            val geocoderBean = httpGeocoder()

            geocoder.value = geocoderBean
        }


    }


    private suspend fun httpGeocoder() = withContext(Dispatchers.IO) {

        delay(1000)
        RetrofitFactory.instance.getService(ApiService::class.java)
            .geocoder().dataConvert()
    }

}