package com.yzq.kotlincommon.mvvm.model

import com.yzq.common.net.RetrofitFactory
import com.yzq.data_constants.data.movie.MovieBean
import com.yzq.kotlincommon.net.ApiService
import io.reactivex.Single


class MoviesModel {

    fun getData(start: Int, count: Int): Single<MovieBean> {

        return RetrofitFactory.instance.getService(ApiService::class.java).getMovies("0b2bdeda43b5688921839c8ecb20399b", start, count)
    }


}