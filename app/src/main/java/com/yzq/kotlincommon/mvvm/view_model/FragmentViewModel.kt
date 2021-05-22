package com.yzq.kotlincommon.mvvm.view_model

import com.yzq.kotlincommon.ui.fragment.TaskFragment
import com.yzq.kotlincommon.ui.fragment.UserFragment
import com.yzq.lib_base.ui.fragment.BaseFragment
import com.yzq.lib_base.view_model.BaseViewModel


class FragmentViewModel : BaseViewModel() {


    val taskFragment by lazy {
        TaskFragment.newInstance()
    }
    val userFragment: UserFragment by lazy {
        UserFragment.newInstance()
    }

    val fragmentList = arrayListOf<BaseFragment>()


}

