package com.yzq.kotlincommon.mvp.model

import com.yzq.common.extend.dataConvert
import com.yzq.common.net.RetrofitFactory
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.net.ApiService
import io.reactivex.Observable
import javax.inject.Inject

class MainModel @Inject constructor() {

    fun getData(type: String = "top", key: String = "4c52313fc9247e5b4176aed5ddd56ad7"): Observable<NewsBean> {

        return RetrofitFactory.instance.getService(ApiService::class.java).getIndex(type, key).dataConvert()
    }
}