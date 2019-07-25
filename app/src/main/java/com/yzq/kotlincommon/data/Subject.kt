package com.yzq.kotlincommon.data


import com.google.gson.annotations.SerializedName

data class Subject(
        var rating: Rating = Rating(),
        var genres: List<String> = listOf(),
        var title: String = "",
        var casts: List<Cast> = listOf(),
        var durations: List<String> = listOf(),
        @SerializedName("collect_count")
        var collectCount: Int = 0,
        @SerializedName("mainland_pubdate")
        var mainlandPubdate: String = "",
        @SerializedName("has_video")
        var hasVideo: Boolean = false,
        @SerializedName("original_title")
        var originalTitle: String = "",
        var subtype: String = "",
        var directors: List<Director> = listOf(),
        var pubdates: List<String> = listOf(),
        var year: String = "",
        var images: Images = Images(),
        var alt: String = "",
        var id: String = ""
)