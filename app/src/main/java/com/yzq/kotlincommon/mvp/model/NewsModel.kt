package com.yzq.kotlincommon.mvp.model

import com.yzq.common.net.RetrofitFactory
import com.yzq.kotlincommon.data.MovieBean
import com.yzq.kotlincommon.net.ApiService
import io.reactivex.Single
import javax.inject.Inject

class NewsModel @Inject constructor() {

    fun getData(start: Int, count: Int): Single<MovieBean> {

        return RetrofitFactory.instance.getService(ApiService::class.java).getMovies("0b2bdeda43b5688921839c8ecb20399b", start, count)
    }


}