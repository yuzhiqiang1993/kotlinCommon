package com.yzq.gao_de_map.ext

import com.amap.api.location.AMapLocationClient
import com.yzq.gao_de_map.LocationResultListener

fun AMapLocationClient.setLocationResultListener(listener: LocationResultListener) {
    setLocationListener(listener)
}