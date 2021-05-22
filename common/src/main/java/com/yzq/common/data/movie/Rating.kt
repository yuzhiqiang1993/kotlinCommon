package com.yzq.common.data.movie


data class Rating(
    var max: Int = 0,
    var average: Double = 0.0,
    var details: Details = Details(),
    var stars: String = "",
    var min: Int = 0
)