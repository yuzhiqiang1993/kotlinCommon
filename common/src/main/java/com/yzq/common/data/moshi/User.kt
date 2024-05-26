package com.yzq.common.data.moshi

data class User(
    var name: String = "",
    var age: Int = 0,
    val hobby: List<Hobby> = mutableListOf()

) {
    data class Hobby(
        val type: String = "",
        val name: String = ""
    )
}