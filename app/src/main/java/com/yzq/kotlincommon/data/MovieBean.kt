package com.yzq.kotlincommon.data


import com.google.gson.annotations.SerializedName

data class MovieBean(
    var count: Int = 0,
    var start: Int = 0,
    var total: Int = 0,
    var subjects: List<Subject> = listOf(),
    var title: String = ""
)