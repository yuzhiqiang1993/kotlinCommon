package com.yzq.kotlincommon.net

import com.yzq.common.data.BaseResp
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.data.request.GetNews
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    @POST(ServerContants.Url.index)
    @FormUrlEncoded
    fun getIndex(@Field(ServerContants.parameter.type) type: String, @Field(ServerContants.parameter.key) key: String): Observable<BaseResp<NewsBean>>


    @POST(ServerContants.Url.index)
    fun getIndex(@Body getNews: GetNews): Observable<BaseResp<NewsBean>>


}