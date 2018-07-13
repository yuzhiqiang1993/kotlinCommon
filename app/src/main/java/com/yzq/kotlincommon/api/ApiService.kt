package com.yzq.kotlincommon.api

import com.yzq.common.data.BaseResp
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("AndroidPatrol/GetPatrolDataSource")
    fun getData(@Query("userName") userName:String="xuncha02" ):Observable<BaseResp<String>>
}