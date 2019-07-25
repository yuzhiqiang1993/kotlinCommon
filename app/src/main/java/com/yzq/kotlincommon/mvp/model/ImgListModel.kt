package com.yzq.kotlincommon.mvp.model

import com.yzq.common.net.RetrofitFactory
import com.yzq.kotlincommon.data.MovieBean
import com.yzq.kotlincommon.net.ApiService
import io.reactivex.Single
import javax.inject.Inject

class ImgListModel @Inject constructor() {
    fun getImgs(start: Int, count: Int): Single<MovieBean> = RetrofitFactory.instance.getService(ApiService::class.java).getMovies(start = start, count = count)
}