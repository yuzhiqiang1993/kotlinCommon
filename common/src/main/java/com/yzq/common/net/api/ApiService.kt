package com.yzq.common.net.api

import com.yzq.common.api.BaseResp
import com.yzq.common.data.gaode.Geocoder
import com.yzq.common.data.github.GithubUserInfo
import com.yzq.common.data.juhe.toutiao.TouTiao
import com.yzq.common.data.moshi.LocalUser
import com.yzq.common.net.constants.ApiConstants
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Streaming

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


    /**
     * 文件上传接口
     *
     * @param requestBody
     * @return
     */
    @POST("http://192.168.0.103:8888/img/uploadImg")
    suspend fun uploadImg(@Body requestBody: RequestBody): String


    /**
     * Post sample
     * post请求可以直接传指定类型的对象数据做为参数，也可以传RequestBody类型
     * @param body
     * @return
     */
    @POST("http://192.168.1.184:8888/user/userList")
    suspend fun postSample(@Body body: LocalUser): BaseResp<LocalUser>

    @POST("http://v.juhe.cn/toutiao/index")
    @FormUrlEncoded
    suspend fun listToutiao(
        @Field("type") type: String = "top",
        @Field("page") page: Int = 1,
        @Field("page_size") pageSize: Int = 10,
        @Field("key") key: String = ApiConstants.juheTouTiaoKey,
    ): TouTiao
}
