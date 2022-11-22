package com.yzq.common.net.api

import com.yzq.common.api.BaseResp
import com.yzq.common.data.gaode.Geocoder
import com.yzq.common.data.github.GithubUserInfo
import com.yzq.common.data.juhe.toutiao.TouTiao
import com.yzq.common.data.moshi.LocalUser
import com.yzq.common.net.constants.ApiConstants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("https://api.map.baidu.com/geocoder/v2/")
    suspend fun geocoder(
        @Query("location") location: String = "31.108768,121.418335",
        @Query("ak") ak: String = "RFVByxDyRXlNDpKGKybtFkz0pEw6mQn0",
        @Query("output") output: String = "json",
        @Query("latest_admin") latest_admin: String = "1",
    ): Response<Geocoder>

    /*下载安装包*/
    @Streaming
    @GET(ApiConstants.apk)
    suspend fun downloadApk(): ResponseBody

    /**
     * User info
     *
     * @return
     */
    @GET("https://api.github.com/users/yuzhiqiang1993")
    suspend fun userInfo(): GithubUserInfo

    @GET("http://192.168.1.184:8888/user/userList")
    suspend fun listLocalUser(): BaseResp<List<LocalUser>>

    @POST("http://v.juhe.cn/toutiao/index")
    @FormUrlEncoded
    suspend fun listToutiao(
        @Field("type") type: String = "top",
        @Field("page") page: Int = 1,
        @Field("page_size") pageSize: Int = 10,
        @Field("key") key: String = ApiConstants.juheTouTiaoKey,
    ): TouTiao
}
