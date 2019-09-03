package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.yzq.lib_rx.transform
import com.yzq.lib_base.view_model.BaseViewModel
import com.yzq.lib_base.rx.BaseObserver
import com.yzq.lib_constants.HttpRequestType
import com.yzq.kotlincommon.data.movie.MovieBean
import com.yzq.kotlincommon.data.movie.Subject
import com.yzq.kotlincommon.mvvm.model.MoviesModel

class ImgListViewModel : BaseViewModel() {


    var start = 0
    var requestType = HttpRequestType.FIRST
    private val count = 30
    private var model: MoviesModel = MoviesModel()

    var subjectsLive = MutableLiveData<List<Subject>>()


    fun getData() {

        model.getData(start, count)
                .transform(lifecycleOwner)
                .subscribe(object : BaseObserver<MovieBean>(this) {
                    override fun onSuccess(movie: MovieBean) {
                        start += count

                        subjectsLive.value = movie.subjects

                    }

                })
    }


}