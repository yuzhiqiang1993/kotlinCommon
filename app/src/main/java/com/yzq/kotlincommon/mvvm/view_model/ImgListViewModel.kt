package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.yzq.common.constants.HttpRequestType
import com.yzq.common.extend.transform
import com.yzq.common.mvvm.BaseViewModel
import com.yzq.common.rx.BaseSingleVmObserver
import com.yzq.kotlincommon.data.MovieBean
import com.yzq.kotlincommon.data.Subject
import com.yzq.kotlincommon.mvvm.model.MoviesModel

class ImgListViewModel : BaseViewModel() {

    var start = 0
    private val count = 30
    private var model: MoviesModel = MoviesModel()

    private var subjects = mutableListOf<Subject>()

    var subjectsLive = MutableLiveData<List<Subject>>()

    fun getData(requestType: Int) {

        if (requestType == HttpRequestType.FIRST || requestType == HttpRequestType.REFRESH) {
            subjects.clear()
            start = 0
        }


        model.getData(start, count)
                .transform(lifecycleOwner)
                .subscribe(object : BaseSingleVmObserver<MovieBean>(this) {
                    override fun onSuccess(movie: MovieBean) {
                        start += count
                        subjects.addAll(movie.subjects)
                        subjectsLive.value = movie.subjects

                    }

                })
    }


}