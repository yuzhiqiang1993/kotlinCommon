package com.yzq.kotlincommon.ui.fragment

import com.yzq.common.ui.BaseFragment
import com.yzq.kotlincommon.R
import kotlinx.android.synthetic.main.task_fragment.*

class TaskFragment : BaseFragment() {
    override fun getContentLayoutId(): Int = R.layout.task_fragment

    companion object {
        fun newInstance() = TaskFragment()
    }

    override fun initWidget() {
        tv_task.text = "喻志强"
    }

}
