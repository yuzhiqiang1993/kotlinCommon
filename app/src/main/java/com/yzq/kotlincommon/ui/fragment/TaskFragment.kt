package com.yzq.kotlincommon.ui.fragment

import androidx.fragment.app.viewModels
import com.yzq.base.extend.observeUIState
import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.base.utils.MoshiUtils
import com.yzq.binding.viewbind
import com.yzq.coroutine.flow.launchCollect
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.TaskFragmentBinding
import com.yzq.kotlincommon.view_model.CoroutineViewModel
import com.yzq.logger.LogCat
import kotlinx.coroutines.flow.filter

class TaskFragment : BaseFragment(R.layout.task_fragment) {

    private val binding by viewbind(TaskFragmentBinding::bind)
    private val vm: CoroutineViewModel by viewModels()

    companion object {
        fun newInstance() = TaskFragment()
    }

    override fun initWidget() {
        LogCat.i("TaskFragment")
        binding.tvTask.text = "喻志强"
        binding.layoutState.onRefresh {
            vm.requestData()
        }.showLoading()
    }

    override fun observeViewModel() {
        observeUIState(vm, stateLayout = binding.layoutState)
        vm.run {
            geocoder.observe(this@TaskFragment) {
                binding.tvTask.text = MoshiUtils.toJson(it, "  ")
            }

            geocoderFlow
                .filter { it != null }
                .launchCollect(this@TaskFragment.viewLifecycleOwner) { // 扩展方法
                    binding.tvTask.text = it.toString()
                }
        }
    }
}
