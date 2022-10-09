package com.yzq.kotlincommon.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankj.utilcode.util.LogUtils
import com.yzq.kotlincommon.databinding.TaskFragmentBinding
import com.yzq.kotlincommon.mvvm.view_model.CoroutineViewModel
import com.yzq.lib_base.ui.fragment.BaseVbVmFragment
import com.yzq.lib_base.utils.MoshiUtils

class TaskFragment : BaseVbVmFragment<TaskFragmentBinding, CoroutineViewModel>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> TaskFragmentBinding
        get() = TaskFragmentBinding::inflate

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
