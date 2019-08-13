package com.yzq.kotlincommon.ui.fragment

import android.os.Bundle
import com.yzq.common.ui.BaseFragment
import com.yzq.kotlincommon.R

class UserFragment : BaseFragment() {
    override fun getContentLayoutId(): Int = R.layout.fragment_user

    companion object {
        fun newInstance() = UserFragment()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}
