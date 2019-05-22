package com.yzq.kotlincommon.data


import com.google.gson.annotations.SerializedName

data class BaiDuImgBean(
        var `data`: List<Data> = listOf(),
        @SerializedName("return_number")
        var returnNumber: Int = 0, // 2
        @SerializedName("start_index")
        var startIndex: Int = 0, // 0
        var tag1: String = "", // 美女
        var tag2: String = "", // 全部
        var totalNum: Int = 0 // 16870
) {
    data class Data(
            var abs: String = "", // 校园清纯甜美女孩走日系风格
            @SerializedName("album_di")
            var albumDi: String = "",
            @SerializedName("album_obj_num")
            var albumObjNum: String = "", // 9
            @SerializedName("app_id")
            var appId: String = "",
            @SerializedName("can_album_id")
            var canAlbumId: String = "",
            @SerializedName("children_vote")
            var childrenVote: String = "",
            @SerializedName("collect_num")
            var collectNum: Int = 0, // 0
            var colum: String = "", // 美女
            var date: String = "", // 2016-10-25
            var desc: String = "", // 校园清纯甜美女孩走日系风格
            @SerializedName("desc_info")
            var descInfo: String = "",
            @SerializedName("dislike_num")
            var dislikeNum: String = "",
            @SerializedName("download_num")
            var downloadNum: Int = 0, // 0
            @SerializedName("download_url")
            var downloadUrl: String = "", // http://f.hiphotos.baidu.com/image/pic/item/b151f8198618367abfffecd72c738bd4b31ce542.jpg
            @SerializedName("dress_buy_link")
            var dressBuyLink: String = "",
            @SerializedName("dress_discount")
            var dressDiscount: String = "",
            @SerializedName("dress_extend_name")
            var dressExtendName: String = "",
            @SerializedName("dress_extend_type")
            var dressExtendType: String = "",
            @SerializedName("dress_id")
            var dressId: String = "",
            @SerializedName("dress_num")
            var dressNum: String = "",
            @SerializedName("dress_other")
            var dressOther: String = "",
            @SerializedName("dress_price")
            var dressPrice: String = "",
            @SerializedName("dress_tag")
            var dressTag: String = "",
            @SerializedName("fashion_id")
            var fashionId: String = "",
            @SerializedName("from_name")
            var fromName: Int = 0, // 0
            @SerializedName("from_url")
            var fromUrl: String = "", // http://www.7ymm.com/qingchun/2011/0901/1839.html
            @SerializedName("fushi_obj_array")
            var fushiObjArray: String? = "", // null
            @SerializedName("fushi_obj_num")
            var fushiObjNum: String = "",
            var hostname: String = "", // www.7ymm.com
            var id: String = "", // 9573911397
            @SerializedName("image_height")
            var imageHeight: Int = 0, // 500
            @SerializedName("image_url")
            var imageUrl: String = "", // http://f.hiphotos.baidu.com/image/pic/item/b151f8198618367abfffecd72c738bd4b31ce542.jpg
            @SerializedName("image_width")
            var imageWidth: Int = 0, // 750
            var isAdapted: Int = 0, // 1
            @SerializedName("is_album")
            var isAlbum: Int = 0, // 0
            @SerializedName("is_single")
            var isSingle: String = "",
            @SerializedName("is_vip")
            var isVip: Int = 0, // 0
            @SerializedName("like_num")
            var likeNum: String = "",
            @SerializedName("obj_url")
            var objUrl: String = "", // http://www.7ymm.com/uploads/allimg/y20110901/eff900cf2671585f38182f213a7876f2.jpg
            @SerializedName("other_urls")
            var otherUrls: List<Any> = listOf(),
            @SerializedName("parent_tag")
            var parentTag: String = "",
            @SerializedName("photo_id")
            var photoId: String = "", // 9573911397
            var pn: Int = 0, // 1
            @SerializedName("return_number")
            var returnNumber: Int = 0, // 2
            var setId: String = "", // 78245
            @SerializedName("share_url")
            var shareUrl: String = "", // http://f.hiphotos.baidu.com/image/s%3D550%3Bc%3Dwantu%2C8%2C95/sign=4d73b90aeb24b899da3c793d5e3d7ea8/b151f8198618367abfffecd72c738bd4b31ce542.jpg?referer=347680ad5bee3d6d7bd1b2fb4340
            @SerializedName("site_logo")
            var siteLogo: String = "",
            @SerializedName("site_name")
            var siteName: String = "",
            @SerializedName("site_url")
            var siteUrl: String = "", // http://www.7ymm.com
            @SerializedName("start_index")
            var startIndex: Int = 0, // 0
            var tag: String = "", // 全部
            var tags: List<String> = listOf(),
            @SerializedName("thumb_large_height")
            var thumbLargeHeight: Int = 0, // 206
            @SerializedName("thumb_large_url")
            var thumbLargeUrl: String = "", // http://imgt9.bdstatic.com/it/u=2,983976805&fm=19&gp=0.jpg
            @SerializedName("thumb_large_width")
            var thumbLargeWidth: Int = 0, // 310
            @SerializedName("thumbnail_height")
            var thumbnailHeight: Int = 0, // 153
            @SerializedName("thumbnail_url")
            var thumbnailUrl: String = "", // http://imgt9.bdstatic.com/it/u=2,983976805&fm=25&gp=0.jpg
            @SerializedName("thumbnail_width")
            var thumbnailWidth: Int = 0, // 230
            @SerializedName("user_id")
            var userId: String = "", // 862713943
            var imgLoaded: Boolean = false
    )
}