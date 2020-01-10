package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.yzq.common.data.movie.Subject
import com.yzq.kotlincommon.mvvm.model.MoviesModel
import com.yzq.lib_base.view_model.BaseViewModel


class MovieViewModel : BaseViewModel() {
    private var start = 0
    private var count = 50
    private val model: MoviesModel by lazy { MoviesModel() }


    var subjects = MutableLiveData<List<Subject>>()


    /*请求数据*/
    fun requestData() {

        launchLoading {
            subjects.value = model.getData(start, count).subjects

        }


    }

}