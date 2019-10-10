package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.yzq.common.extend.dataConvert
import com.yzq.kotlincommon.data.gaode.Geocoder
import com.yzq.kotlincommon.net.ApiService
import com.yzq.lib_base.view_model.BaseViewModel
import com.yzq.lib_net.net.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class CoroutineViewModel : BaseViewModel() {


    var geocoder = MutableLiveData<Geocoder>()


    /*请求数据*/
    fun requestData() {

        launchLoading {

            val geocoderBean = httpGeocoder()

            geocoder.value = geocoderBean
        }


    }


    suspend fun httpGeocoder() = withContext(Dispatchers.IO) {

        delay(1000)
        RetrofitFactory.instance.getService(ApiService::class.java)
            .geocoder().dataConvert()
    }

}