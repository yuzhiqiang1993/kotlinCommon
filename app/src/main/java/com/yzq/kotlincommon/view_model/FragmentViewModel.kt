package com.yzq.kotlincommon.view_model

import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.base.view_model.BaseViewModel
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
}
