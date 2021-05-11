package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.data.gaode.Geocoder
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.common.net.ext.dataConvert
import com.yzq.lib_base.view_model.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.lang.Thread.currentThread


class CoroutineViewModel : BaseViewModel() {


    val geocoder by lazy { MutableLiveData<Geocoder>() }


    /*请求数据*/
    fun requestData() {

        launchLoading {


            geocoder.value=RetrofitFactory.instance.getService(ApiService::class.java).geocoder().dataConvert()

        }


    }


}