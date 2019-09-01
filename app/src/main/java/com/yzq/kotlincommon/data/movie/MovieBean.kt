package com.yzq.kotlincommon.data.movie


data class MovieBean(
        var count: Int = 0,
        var start: Int = 0,
        var total: Int = 0,
        var subjects: List<Subject> = listOf(),
        var title: String = ""
)