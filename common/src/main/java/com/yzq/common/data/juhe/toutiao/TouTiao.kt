package com.yzq.common.data.juhe.toutiao

import com.squareup.moshi.Json

data class TouTiao(
    @Json(name = "error_code")
    val errorCode: Int = 0, // 0
    @Json(name = "reason")
    val reason: String = "", // success
    @Json(name = "result")
    val result: Result? = null,
) {
    data class Result(
        @Json(name = "data")
        val `data`: List<Data> = listOf(),
        @Json(name = "page")
        val page: String = "", // 1
        @Json(name = "pageSize")
        val pageSize: String = "", // 1
        @Json(name = "stat")
        val stat: String = "", // 1
    ) {
        data class Data(
            @Json(name = "author_name")
            val authorName: String = "", // 每日看点快看
            @Json(name = "category")
            val category: String = "", // 头条
            @Json(name = "date")
            val date: String = "", // 2022-11-18 10:18:00
            @Json(name = "is_content")
            val isContent: String = "", // 1
            @Json(name = "thumbnail_pic_s")
            val thumbnailPicS: String = "", // https://dfzximg02.dftoutiao.com/news/20221118/20221118101837_7d38a1292d5982ba21c9e23b15187560_1_mwpm_03201609.jpeg
            @Json(name = "title")
            val title: String = "", // 环卫工人：汗水换来城市美
            @Json(name = "uniquekey")
            val uniquekey: String = "", // b1899fdbd07f7bf99ce3799c18acd748
            @Json(name = "url")
            val url: String = "", // https://mini.eastday.com/mobile/221118101837563684680.html
        )
    }
}
