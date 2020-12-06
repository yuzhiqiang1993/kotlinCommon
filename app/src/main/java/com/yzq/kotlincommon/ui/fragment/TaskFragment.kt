package com.yzq.kotlincommon.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.net.GsonConvert
import com.yzq.kotlincommon.databinding.TaskFragmentBinding
import com.yzq.kotlincommon.mvvm.view_model.CoroutineViewModel
import com.yzq.lib_base.ui.fragment.BaseVbVmFragment


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

        initStateView(binding.stateView, binding.tvTask)

    }

    override fun initData() {

        showLoading()
        vm.requestData()
    }

    override fun observeViewModel() {

        with(vm) {
            geocoder.observe(this@TaskFragment, Observer {
                binding.tvTask.text = GsonConvert.toJson(it)

                showContent()
            })
        }

    }


}
