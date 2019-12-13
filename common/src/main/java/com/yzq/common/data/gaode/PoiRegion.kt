package com.yzq.kotlincommon.data.gaode


import com.google.gson.annotations.SerializedName

data class PoiRegion(
    @SerializedName("direction_desc")
    var directionDesc: String = "",
    var name: String = "",
    var tag: String = "",
    var uid: String = "",
    var distance: String = ""
)