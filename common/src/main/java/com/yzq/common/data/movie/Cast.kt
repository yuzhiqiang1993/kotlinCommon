package com.yzq.common.data.movie


import com.google.gson.annotations.SerializedName

data class Cast(
        var avatars: Avatars = Avatars(),
        @SerializedName("name_en")
    var nameEn: String = "",
        var name: String = "",
        var alt: String = "",
        var id: String = ""
)