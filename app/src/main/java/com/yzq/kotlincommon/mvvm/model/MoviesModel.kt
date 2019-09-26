package com.yzq.kotlincommon.mvvm.model

import com.yzq.kotlincommon.data.movie.MovieBean
import com.yzq.kotlincommon.net.ApiService
import com.yzq.lib_net.net.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MoviesModel {

    suspend fun getData(start: Int, count: Int): MovieBean = withContext(Dispatchers.IO) {
        RetrofitFactory.instance.getService(ApiService::class.java)
            .getMovies("0b2bdeda43b5688921839c8ecb20399b", start, count)
    }
}