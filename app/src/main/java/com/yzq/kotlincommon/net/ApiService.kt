package com.yzq.kotlincommon.net

import com.yzq.common.data.BaseResp
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.data.request.GetNews
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST(ApiConstants.index)
    @FormUrlEncoded
    fun getIndex(@Field(ParamConstants.type) type: String, @Field(ParamConstants.key) key: String): Observable<BaseResp<NewsBean>>


    @POST(ApiConstants.index)
    fun getIndex(@Body getNews: GetNews): Observable<BaseResp<NewsBean>>


}