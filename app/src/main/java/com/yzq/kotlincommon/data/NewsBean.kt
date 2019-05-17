package com.yzq.kotlincommon.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class NewsBean(var stat: String = "", // 1
                    var data: List<Data> = listOf()) {
    data class Data(

            @Expose
            var uniquekey: String = "", // fe1989479c791a1898852edf3b7ed001
            @Expose
            var title: String = "", // 古装美人饮酒，赵丽颖俏皮，李沁优雅，杨幂豪爽，而她秒杀众人
            @Expose
            var date: String = "", // 2018-07-13 14:17
            @Expose
            var category: String = "", // 头条
            @Expose
            @SerializedName("author_name")
            var authorName: String = "", // 北青网
            @Expose
            var url: String = "", // http://mini.eastday.com/mobile/180713141718664.html
            @Expose
            @SerializedName("thumbnail_pic_s")
            var thumbnailPicS: String = "", // http://05.imgmini.eastday.com/mobile/20180713/20180713141718_bebdf00d5070a17241f48e305bc76f75_3_mwpm_03200403.jpg
            @SerializedName("thumbnail_pic_s02")
            @Expose
            var thumbnailPicS02: String = "", // http://05.imgmini.eastday.com/mobile/20180713/20180713141718_bebdf00d5070a17241f48e305bc76f75_4_mwpm_03200403.jpg
            @SerializedName("thumbnail_pic_s03")
            @Expose
            var thumbnailPicS03: String = "" // http://05.imgmini.eastday.com/mobile/20180713/20180713141718_bebdf00d5070a17241f48e305bc76f75_5_mwpm_03200403.jpg

    )
}