package com.yzq.kotlincommon.net

import com.yzq.data_constants.constants.net.ApiConstants
import com.yzq.data_constants.constants.net.ParamConstants
import com.yzq.data_constants.data.movie.MovieBean
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(ApiConstants.top250)
    fun getMovies(@Query(ParamConstants.key) key: String = "0b2bdeda43b5688921839c8ecb20399b", @Query(ParamConstants.start) start: Int = 0, @Query(ParamConstants.count) count: Int = 10): Single<MovieBean>


}