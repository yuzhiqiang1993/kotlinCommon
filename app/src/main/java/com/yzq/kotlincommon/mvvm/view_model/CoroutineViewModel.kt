package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.yzq.common.data.gaode.Geocoder
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.common.net.ext.dataConvert
import com.yzq.lib_base.view_model.BaseViewModel
import kotlinx.coroutines.delay


class CoroutineViewModel : BaseViewModel() {


    val geocoder by lazy { MutableLiveData<Geocoder>() }


    /*请求数据*/
    fun requestData() {
        launchLoading {

            delay(3000)

            geocoder.value =
                RetrofitFactory.instance.getService(ApiService::class.java).geocoder()
                    .dataConvert()

//            launch(Dispatchers.IO) {
//                throw Exception("ex")
//            }
//
//
//
//            val async1 = async {
//
//                LogUtils.i("async 111111111111")
//
//                delay(200)
//
//                "async 执行完成"
//            }
//
//
//            val async2 = async {
//
//                withContext(Dispatchers.IO) {
//                    LogUtils.i("async 222222222222")
//
//                    delay(100)
//                    throw Exception("async2")
//                }
//
//            }
//
//            val await1 = async1.await()
//            LogUtils.i("await1:$await1")
//            async2.await()


        }


    }


}