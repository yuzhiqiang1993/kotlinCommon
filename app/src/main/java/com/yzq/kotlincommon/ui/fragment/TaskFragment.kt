package com.yzq.kotlincommon.ui.fragment

import com.blankj.utilcode.util.LogUtils
import com.yzq.base.ui.fragment.BaseVmFragment
import com.yzq.base.utils.MoshiUtils
import com.yzq.binding.viewbind
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.TaskFragmentBinding
import com.yzq.kotlincommon.view_model.CoroutineViewModel

class TaskFragment : BaseVmFragment<CoroutineViewModel>(R.layout.task_fragment) {
    private val binding by viewbind(TaskFragmentBinding::bind)

    override fun getViewModelClass(): Class<CoroutineViewModel> = CoroutineViewModel::class.java

    companion object {
        fun newInstance() = TaskFragment()
    }

    override fun initWidget() {
        LogUtils.i("TaskFragment")
        binding.tvTask.text = "喻志强"

        stateViewManager.initStateView(binding.stateView, binding.tvTask)

    }

    override fun initData() {
        vm.requestData()
    }

    override fun observeViewModel() {

        vm.run {
            geocoder.observe(this@TaskFragment) {
                binding.tvTask.text = MoshiUtils.toJson(it, "  ")

                stateViewManager.showContent()
            }
        }

    }

}
