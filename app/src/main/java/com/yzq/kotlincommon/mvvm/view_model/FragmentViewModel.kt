package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.yzq.common.mvvm.view_model.BaseViewModel
import com.yzq.kotlincommon.ui.fragment.TaskFragment
import com.yzq.kotlincommon.ui.fragment.UserFragment


class FragmentViewModel : BaseViewModel() {


    val taskFragment = TaskFragment()
    val userFragment = UserFragment()


    val fragmentList = arrayListOf(taskFragment, userFragment)

    var tabSelectedPosition = MutableLiveData<Int>()

    fun changeTabSelected(position: Int) {

        tabSelectedPosition.postValue(position)

    }


}