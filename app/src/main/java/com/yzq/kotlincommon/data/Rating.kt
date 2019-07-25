package com.yzq.kotlincommon.data


import com.google.gson.annotations.SerializedName

data class Rating(
    var max: Int = 0,
    var average: Double = 0.0,
    var details: Details = Details(),
    var stars: String = "",
    var min: Int = 0
)