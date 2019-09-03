package com.yzq.kotlincommon.ui.fragment

import com.blankj.utilcode.util.LogUtils
import com.yzq.lib_base.ui.BaseFragment
import com.yzq.kotlincommon.R

class UserFragment : BaseFragment() {
    override fun getContentLayoutId(): Int = R.layout.fragment_user

    companion object {
        fun newInstance() = UserFragment()
    }


    override fun initWidget() {

        LogUtils.i("UserFragment")
    }

}
