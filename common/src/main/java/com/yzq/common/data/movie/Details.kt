package com.yzq.kotlincommon.data.movie


import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("1")
    var x1: Double = 0.0,
    @SerializedName("3")
    var x3: Double = 0.0,
    @SerializedName("2")
    var x2: Double = 0.0,
    @SerializedName("5")
    var x5: Double = 0.0,
    @SerializedName("4")
    var x4: Double = 0.0
)