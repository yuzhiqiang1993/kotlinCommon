package com.yzq.kotlincommon.mvp.view_model

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.extend.transform
import com.yzq.common.mvvm.BaseViewModel
import com.yzq.common.rx.BaseSingleVmObserver
import com.yzq.kotlincommon.data.MovieBean
import com.yzq.kotlincommon.data.Subject
import com.yzq.kotlincommon.mvp.model.NewsModel

class MovieViewModel : BaseViewModel() {

    var model: NewsModel = NewsModel()


    var subjects = MutableLiveData<List<Subject>>()


    /*请求数据*/
    fun requestData(start: Int, count: Int) {

        model.getData(start, count)
                .transform(lifecycleOwner)
                .subscribe(object : BaseSingleVmObserver<MovieBean>(this) {
                    override fun onSuccess(movieBean: MovieBean) {

                        LogUtils.i("请求成功")
                        subjects.value = movieBean.subjects
                    }

                })


    }

}