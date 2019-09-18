package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.yzq.kotlincommon.data.movie.Subject
import com.yzq.kotlincommon.mvvm.model.MoviesModel
import com.yzq.lib_base.view_model.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class CoroutineViewModel : BaseViewModel() {
    private var start = 0
    private var count = 1
    var model: MoviesModel = MoviesModel()


    var subjects = MutableLiveData<List<Subject>>()


    /*请求数据*/
    fun requestData() {


        launchLoading {

            val movieBean = withContext(Dispatchers.IO) {

                delay(2000)
                model.getData1(start, count)

            }

            subjects.value = movieBean.subjects
        }


    }

}