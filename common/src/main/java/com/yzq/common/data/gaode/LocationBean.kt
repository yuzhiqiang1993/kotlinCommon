package com.yzq.kotlincommon.data.gaode

data class LocationBean(
        /*经度*/
        var longitude: Double = 0.0,
        /*纬度*/
        var latitude: Double = 0.0,
        /*国家*/
        var country: String = "",
        /*省*/
        var province: String = "",
        /*市*/
        var city: String = "",
        /*城市编码*/
        var cityCode: String = "",
        /*区*/
        var district: String = "",
        /*区域编码*/
        var districtCode: String = "",
        /*地址*/
        var address: String = "",
        /*定位时间*/
        var time: Long = 0L

)
