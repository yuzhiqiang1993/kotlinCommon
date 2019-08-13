package com.yzq.kotlincommon.mvvm.view_model

import com.yzq.common.mvvm.view_model.BaseViewModel
import com.yzq.common.ui.BaseFragment
import com.yzq.kotlincommon.ui.fragment.TaskFragment
import com.yzq.kotlincommon.ui.fragment.UserFragment


class FragmentViewModel : BaseViewModel() {


    val taskFragment by lazy {
        TaskFragment.newInstance()
    }
    val userFragment: UserFragment by lazy {
        UserFragment.newInstance()
    }

    val fragmentList = arrayListOf<BaseFragment>()


    fun addFragment(fragment: BaseFragment) {

        if (!fragmentList.contains(fragment)) {
            fragmentList.add(fragment)
        }
    }

}

