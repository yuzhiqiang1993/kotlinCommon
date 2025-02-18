package com.yzq.amap

import androidx.lifecycle.MutableLiveData
import com.yzq.baseui.UIState
import com.yzq.baseui.UiStateViewModel
import com.yzq.location_manager.LocationManager
import com.yzq.location_protocol.callback.LocationListener
import com.yzq.location_protocol.data.Location

/**
 * @description: 定位模块,签到模式
 * @author : yzq
 *
 */

class SignLocationViewModel : UiStateViewModel() {


    val locationLiveData = MutableLiveData<Location>()

    /**
     * 开始定位
     */
    fun startLocation() {
        _uiStateFlow.value =
            UIState.ShowLoadingDialog("正在定位...")
        LocationManager.startOnceLocation(object : LocationListener {
            override fun onReceiveLocation(location: Location) {
                _uiStateFlow.value = UIState.DismissLoadingDialog
                locationLiveData.value = location
            }

            override fun onFailed(errorCode: Int, errorInfo: String, cacheLocation: Location?) {
                _uiStateFlow.value = UIState.DismissLoadingDialog
            }
        })
    }


}

