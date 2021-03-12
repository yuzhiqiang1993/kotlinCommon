package com.yzq.common.net.api

import com.yzq.common.data.movie.MovieBean
import com.yzq.common.net.RetrofitFactory

class ApiServiceModel {

    suspend fun getData(start: Int, count: Int): MovieBean =
            RetrofitFactory.instance.getService(ApiService::class.java)
                    .getMovies("0b2bdeda43b5688921839c8ecb20399b", start, count)


}