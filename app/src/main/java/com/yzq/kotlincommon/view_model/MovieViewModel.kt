package com.yzq.kotlincommon.view_model

import androidx.lifecycle.MutableLiveData
import com.yzq.common.data.movie.Subject
import com.yzq.common.net.view_model.ApiServiceViewModel

class MovieViewModel : ApiServiceViewModel() {
    private var start = 0
    private var count = 50

    var subjects = MutableLiveData<MutableList<Subject>>()

    /*请求数据*/
    fun requestData() {

        launchLoading {
            subjects.value = apiServiceModel.getData(start, count).subjects
        }
    }
}
