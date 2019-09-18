package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.yzq.kotlincommon.data.movie.MovieBean
import com.yzq.kotlincommon.data.movie.Subject
import com.yzq.kotlincommon.mvvm.model.MoviesModel
import com.yzq.lib_base.rx.BaseObserver
import com.yzq.lib_base.view_model.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MovieViewModel : BaseViewModel() {
    private var start = 0
    private var count = 50
    var model: MoviesModel = MoviesModel()


    var subjects = MutableLiveData<List<Subject>>()


    /*请求数据*/
    fun requestData() {

        launchLoading {
            subjects.value = withContext(Dispatchers.IO) {
                model.getData(start, count)
            }.subjects

        }

//        model.getData(start, count)
//            .transform(lifecycleOwner)
//            .subscribe(object : BaseObserver<MovieBean>(this) {
//                override fun onSuccess(movieBean: MovieBean) {
//
//                    LogUtils.i("请求成功")
//                    subjects.value = movieBean.subjects
//                }
//
//            })


    }

}