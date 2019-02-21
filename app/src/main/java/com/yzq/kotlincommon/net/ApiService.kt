package com.yzq.kotlincommon.net

import com.yzq.common.data.BaseResp
import com.yzq.kotlincommon.data.NewsBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST(ServerContants.Url.index)
    @FormUrlEncoded
    fun getIndex(@Field(ServerContants.parameter.type) type: String, @Field(ServerContants.parameter.key) key: String): Observable<BaseResp<NewsBean>>


}