package com.yzq.common.data.movie


import com.squareup.moshi.Json

data class Subject(
    var rating: Rating = Rating(),
    var genres: List<String> = listOf(),
    var title: String = "",
    var casts: List<Cast> = listOf(),
    var durations: List<String> = listOf(),
    @Json(name = "collect_count")
    var collectCount: Int = 0,
    @Json(name = "mainland_pubdate")
    var mainlandPubdate: String = "",
    @Json(name = "has_video")
    var hasVideo: Boolean = false,
    @Json(name = "original_title")
    var originalTitle: String = "",
    var subtype: String = "",
    var directors: List<Director> = listOf(),
    var pubdates: List<String> = listOf(),
    var year: String = "",
    var images: Images = Images(),
    var alt: String = "",
    var id: String = ""
)