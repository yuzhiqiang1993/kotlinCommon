package com.yzq.kotlincommon.mvp.model

import com.yzq.common.data.BaseResp
import com.yzq.common.extend.dataConvert
import com.yzq.common.net.RetrofitFactory
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.data.request.GetNews
import com.yzq.kotlincommon.net.ApiService
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class NewsModel @Inject constructor() {

    fun getData(type: String = "top", key: String = "4c52313fc9247e5b4176aed5ddd56ad7"): Observable<NewsBean> {

        val getNews = GetNews(type = type, key = key)

        return RetrofitFactory.instance.getService(ApiService::class.java).getIndex(type, key).dataConvert()
    }

    fun getNews(type: String = "top", key: String = "4c52313fc9247e5b4176aed5ddd56ad7"): Deferred<BaseResp<NewsBean>> {
        return RetrofitFactory.instance.getService(ApiService::class.java).getIndexDeferred(type, key)
    }
}