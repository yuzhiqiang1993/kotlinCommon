package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.yzq.common.data.movie.Subject
import com.yzq.kotlincommon.mvvm.model.MoviesModel
import com.yzq.lib_base.view_model.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImgListViewModel : BaseViewModel() {


    var start = 0
    private val count = 30
    private val model: MoviesModel by lazy { MoviesModel() }

    val subjectsLive by lazy { MutableLiveData<List<Subject>>() }
    val subjectsDiffResult by lazy { MutableLiveData<DiffUtil.DiffResult>() }


    fun getData() {
        launchLoading {
            val datas = model.getData(start, count).subjects
            subjectsLive.value = datas
            start += datas.size
        }
    }


    /**
     * 计算旧数据和新数据之间的差异值
     * @param oldData List<Subject>
     * @param newData List<Subject>
     */
    fun calculateDiff(oldDatas: List<Subject>, newDatas: List<Subject>) {

        viewModelScope.launch {

            subjectsDiffResult.value = withContext(Dispatchers.IO) {

                DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                    override fun areItemsTheSame(
                        oldItemPosition: Int,
                        newItemPosition: Int
                    ): Boolean {
                        return oldDatas[oldItemPosition].id.equals(newDatas[newItemPosition].id)
                    }

                    override fun getOldListSize(): Int {
                        return oldDatas.size
                    }

                    override fun getNewListSize(): Int {
                        return newDatas.size
                    }

                    override fun areContentsTheSame(
                        oldItemPosition: Int,
                        newItemPosition: Int
                    ): Boolean {
                        return oldDatas[oldItemPosition] == newDatas[newItemPosition]
                    }

                })

            }
        }


    }
}
