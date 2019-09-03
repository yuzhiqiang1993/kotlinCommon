package com.yzq.kotlincommon.ui.fragment

import com.blankj.utilcode.util.LogUtils
import com.yzq.lib_base.ui.BaseFragment
import com.yzq.kotlincommon.R
import kotlinx.android.synthetic.main.task_fragment.*

class TaskFragment : BaseFragment() {
    override fun getContentLayoutId(): Int = R.layout.task_fragment

    companion object {
        fun newInstance() = TaskFragment()
    }

    override fun initWidget() {


        LogUtils.i("TaskFragment")
        tv_task.text = "喻志强"
    }

}
