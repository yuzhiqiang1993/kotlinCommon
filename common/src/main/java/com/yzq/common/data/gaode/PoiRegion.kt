package com.yzq.common.data.gaode


import com.squareup.moshi.Json

data class PoiRegion(
    @Json(name = "direction_desc")
    var directionDesc: String = "",
    var name: String = "",
    var tag: String = "",
    var uid: String = "",
    var distance: String = ""
)