package com.yzq.gao_de_map

import androidx.lifecycle.MutableLiveData
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.yzq.base.view_model.BaseViewModel
import com.yzq.base.view_model.UIState
import com.yzq.gao_de_map.ext.setLocationResultListener
import com.yzq.logger.LogCat

/**
 * @description: 定位模块,签到模式
 * @author : yzq
 * @date : 2018/11/12
 * @time : 18:02
 *
 */

class SignLocationViewModel : BaseViewModel(), LocationResultListener {


    private var singnInLocationClient: AMapLocationClient? = null

    var locationData = MutableLiveData<AMapLocation>()

    /*开始定位*/

    fun startLocation() {
        if (singnInLocationClient == null) {
            LogCat.i("首次创建")
            singnInLocationClient = LocationManager.newSigninLocationClient()
                .apply { setLocationResultListener(this@SignLocationViewModel) }
        }
        _uiStateFlow.value = UIState.ShowLoadingDialog("正在定位...")
        singnInLocationClient?.startLocation()
    }

    override fun onSuccess(location: AMapLocation) {
        locationData.value = location
        _uiStateFlow.value = UIState.DissmissLoadingDialog()
    }

    override fun onFailed(location: AMapLocation) {
        LogCat.i("定位失败了")
    }

    override fun onCleared() {
        super.onCleared()
        LogCat.i("viewmodel 要被销毁了")
        LocationManager.destoryLocationClient(singnInLocationClient)
    }


}

