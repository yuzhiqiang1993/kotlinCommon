package com.yzq.common.data.movie

import com.squareup.moshi.Json


data class Details(
    @Json(name = "1")
    var x1: Double = 0.0,
    @Json(name = "3")
    var x3: Double = 0.0,
    @Json(name = "2")
    var x2: Double = 0.0,
    @Json(name = "5")
    var x5: Double = 0.0,
    @Json(name = "4")
    var x4: Double = 0.0
)