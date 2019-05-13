package com.yzq.kotlincommon.net

import com.yzq.common.data.BaseResp
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.data.request.GetNews
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiService {

    @POST(ServerContants.Url.index)
    @FormUrlEncoded
    fun getIndex(@Field(ServerContants.parameter.type) type: String, @Field(ServerContants.parameter.key) key: String): Observable<BaseResp<NewsBean>>


    @POST(ServerContants.Url.index)
    fun getIndex(@Body getNews: GetNews): Observable<BaseResp<NewsBean>>


    @GET(ServerContants.Url.index)
    fun getIndexDeferred(@Query(ServerContants.parameter.type) type: String, @Query(ServerContants.parameter.key) key: String): Deferred<BaseResp<NewsBean>>

}