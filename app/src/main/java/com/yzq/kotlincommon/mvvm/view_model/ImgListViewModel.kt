package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.yzq.kotlincommon.data.movie.MovieBean
import com.yzq.kotlincommon.data.movie.Subject
import com.yzq.kotlincommon.mvvm.model.MoviesModel
import com.yzq.lib_base.rx.BaseObserver
import com.yzq.lib_base.view_model.BaseViewModel
import com.yzq.lib_rx.transform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImgListViewModel : BaseViewModel() {


    var start = 0
    private val count = 30
    private var model: MoviesModel = MoviesModel()

    var subjectsLive = MutableLiveData<List<Subject>>()


    fun getData() {


        launchLoading {

            subjectsLive.value = withContext(Dispatchers.IO) {
                model.getData(start, count)
            }.subjects

        }

//        model.getData(start, count)
//            .transform(lifecycleOwner)
//            .subscribe(object : BaseObserver<MovieBean>(this) {
//                override fun onSuccess(movie: MovieBean) {
//                    start += count
//
//                    subjectsLive.value = movie.subjects
//
//                }
//
//            })


    }


}