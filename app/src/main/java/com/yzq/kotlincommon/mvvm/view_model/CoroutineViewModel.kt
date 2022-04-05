package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.yzq.common.data.gaode.Geocoder
import com.yzq.common.ext.dataConvert
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.lib_base.view_model.BaseViewModel

class CoroutineViewModel : BaseViewModel() {
    val geocoder by lazy { MutableLiveData<Geocoder>() }

    /*请求数据*/
    fun requestData() {

        launchLoading {

//            RetrofitFactory.instance.getService(ApiService::class.java).userInfo()
            geocoder.value =
                RetrofitFactory.instance.getService(ApiService::class.java).geocoder()
                    .dataConvert()
        }
    }
}