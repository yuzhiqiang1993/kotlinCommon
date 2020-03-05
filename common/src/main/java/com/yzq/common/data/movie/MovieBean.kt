package com.yzq.common.data.movie


data class MovieBean(
    var count: Int = 0,
    var start: Int = 0,
    var total: Int = 0,
    var subjects: MutableList<Subject> = mutableListOf(),
    var title: String = ""
)