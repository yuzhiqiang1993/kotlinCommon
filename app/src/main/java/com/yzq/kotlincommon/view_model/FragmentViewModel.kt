package com.yzq.kotlincommon.view_model

import com.yzq.baseui.BaseFragment
import com.yzq.baseui.BaseViewModel
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
