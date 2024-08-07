package com.yzq.gao_de_map

import androidx.lifecycle.MutableLiveData
import com.yzq.base.view_model.BaseViewModel
import com.yzq.base.view_model.UIState
import com.yzq.location_manager.LocationManager
import com.yzq.location_protocol.callback.LocationListener
import com.yzq.location_protocol.data.Location

/**
 * @description: 定位模块,签到模式
 * @author : yzq
 * @date : 2018/11/12
 * @time : 18:02
 *
 */

class SignLocationViewModel : BaseViewModel() {


    val locationLiveData = MutableLiveData<Location>()

    /**
     * 开始定位
     */
    fun startLocation() {
        _uiStateFlow.value = UIState.ShowLoadingDialog("正在定位...")
        LocationManager.startOnceLocation(object : LocationListener {
            override fun onReceiveLocation(location: Location) {
                _uiStateFlow.value = UIState.DissmissLoadingDialog
                locationLiveData.value = location
            }

            override fun onFailed(errorCode: Int, errorInfo: String, cacheLocation: Location?) {
                _uiStateFlow.value = UIState.DissmissLoadingDialog

            }
        })
    }


}

