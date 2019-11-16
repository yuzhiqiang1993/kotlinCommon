package com.yzq.kotlincommon.ui.fragment

import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.mvvm.view_model.CoroutineViewModel
import com.yzq.lib_base.ui.BaseMvvmFragment
import com.yzq.common.net.GsonConvert
import kotlinx.android.synthetic.main.task_fragment.*

class TaskFragment : BaseMvvmFragment<CoroutineViewModel>() {
    override fun getViewModelClass(): Class<CoroutineViewModel> = CoroutineViewModel::class.java
    override fun getContentLayoutId(): Int = R.layout.task_fragment

    companion object {
        fun newInstance() = TaskFragment()
    }

    override fun initWidget() {
        LogUtils.i("TaskFragment")
        tv_task.text = "喻志强"

        initStateView(state_view,tv_task)

    }

    override fun initData() {

        showLoadding()
        vm.requestData()
    }

    override fun observeViewModel() {

        with(vm) {
            geocoder.observe(this@TaskFragment, Observer {
                tv_task.text = GsonConvert.toJson(it)

                showContent()
            })
        }

    }


}
