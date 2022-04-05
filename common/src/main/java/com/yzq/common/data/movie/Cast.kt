package com.yzq.common.data.movie


import com.squareup.moshi.Json


data class Cast(
    var avatars: Avatars = Avatars(),
    @Json(name = "name_en")
    var nameEn: String = "",
    var name: String = "",
    var alt: String = "",
    var id: String = ""
)