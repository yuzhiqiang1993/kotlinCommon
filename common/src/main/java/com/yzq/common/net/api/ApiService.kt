package com.yzq.common.net.api

import com.yzq.common.api.BaseResp
import com.yzq.common.data.gaode.Geocoder
import com.yzq.common.data.github.GithubUserInfo
import com.yzq.common.data.moshi.LocalUser
import com.yzq.common.data.movie.MovieBean
import com.yzq.common.net.constants.ApiConstants
import com.yzq.common.net.constants.ParamConstants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET(ApiConstants.top250)
    suspend fun getMovies(
        @Query(ParamConstants.key) key: String = "0b2bdeda43b5688921839c8ecb20399b", @Query(
            ParamConstants.start
        ) start: Int = 0, @Query(ParamConstants.count) count: Int = 10
    ): MovieBean

    @GET("https://api.map.baidu.com/geocoder/v2/")
    suspend fun geocoder(
        @Query("location") location: String = "31.108768,121.418335",
        @Query("ak") ak: String = "RFVByxDyRXlNDpKGKybtFkz0pEw6mQn0",
        @Query("output") output: String = "json",
        @Query("latest_admin") latest_admin: String = "1"
    ): Response<Geocoder>

    /*下载安装包*/
    @Streaming
    @GET(ApiConstants.apk)
    suspend fun downloadApk(): ResponseBody

    @GET("https://api.github.com/users/yuzhiqiang1993")
    suspend fun userInfo(): GithubUserInfo

    @GET("http://192.168.1.184:8888/user/userList")
    suspend fun listLocalUser(): BaseResp<List<LocalUser>>
}