package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.yzq.kotlincommon.data.movie.Subject
import com.yzq.kotlincommon.mvvm.model.MoviesModel
import com.yzq.lib_base.view_model.BaseViewModel

class ImgListViewModel : BaseViewModel() {


    var start = 0
    private val count = 30
    private val model: MoviesModel by lazy { MoviesModel() }

    val subjectsLive by lazy { MutableLiveData<List<Subject>>() }


    fun getData() {
        launchLoading {
            subjectsLive.value = model.getData(start, count).subjects
        }
    }
}
