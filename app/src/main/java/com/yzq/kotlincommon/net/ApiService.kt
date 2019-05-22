package com.yzq.kotlincommon.net

import com.yzq.common.data.BaseResp
import com.yzq.kotlincommon.data.BaiDuImgBean
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.data.request.GetNews
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiService {

    @POST(ApiConstants.index)
    @FormUrlEncoded
    fun getIndex(@Field(ParamConstants.type) type: String, @Field(ParamConstants.key) key: String): Observable<BaseResp<NewsBean>>


    @POST(ApiConstants.index)
    fun getIndex(@Body getNews: GetNews): Observable<BaseResp<NewsBean>>


    @GET(ApiConstants.index)
    fun getIndexDeferred(@Query(ParamConstants.type) type: String, @Query(ParamConstants.key) key: String): Deferred<BaseResp<NewsBean>>


    @GET
    fun getGrilsImg(@Url string: String = UrlConstants.BAIDU_IMG, @Query(ParamConstants.page) page: String = "0", @Query(ParamConstants.size) size: String = "1", @Query(ParamConstants.tag) tag: String = "美女"): Observable<String>
}