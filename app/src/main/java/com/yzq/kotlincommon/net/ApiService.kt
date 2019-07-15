package com.yzq.kotlincommon.net

import com.yzq.common.data.BaseResp
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.data.request.GetNews
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST(ApiConstants.index)
    @FormUrlEncoded
    fun getIndex(@Field(ParamConstants.type) type: String, @Field(ParamConstants.key) key: String): Single<BaseResp<NewsBean>>


    @POST(ApiConstants.index)
    fun getIndex(@Body getNews: GetNews): Observable<BaseResp<NewsBean>>


}