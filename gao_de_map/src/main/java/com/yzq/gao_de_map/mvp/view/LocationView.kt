package com.yzq.gao_de_map.mvp.view

import com.yzq.gao_de_map.data.LocationBean


/**
 * @description: 定位接口
 * @author : yzq
 * @date   : 2018/11/12
 * @time   : 18:17
 *
 */

interface LocationView {
    //fun startLocation()
    fun locationSuccess(location: LocationBean)
    fun locationFailed(locationDetail: String)
}