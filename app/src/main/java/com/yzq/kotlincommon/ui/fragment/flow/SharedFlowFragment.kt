package com.yzq.kotlincommon.ui.fragment.flow

import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.baseui.BaseFragment
import com.yzq.binding.viewBinding
import com.yzq.coroutine.ext.launchSafety
import com.yzq.coroutine.flow.debounce
import com.yzq.coroutine.flow.launchCollect
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.FragmentSharedFlowBinding
import com.yzq.logger.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * @description stateflow & sharedflow 使用示例
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/12/28
 * @time    18:25
 */

class SharedFlowFragment : BaseFragment(R.layout.fragment_shared_flow) {

    companion object {
        fun newInstance() = SharedFlowFragment()
    }

    private val viewModel: SharedFlowViewModel by viewModels()

    private val binding by viewBinding(FragmentSharedFlowBinding::bind)


    override fun initWidget() {

        binding.run {

            btnSharedflowEmit.setOnThrottleTimeClick {
                /*让sharedFlow开始生产数据*/
                viewModel.sharedFlowEmit()
            }

            btnSharedflowCollect.setOnThrottleTimeClick {
                /*开始消费sharedflow数据*/
                lifecycleScope.launchSafety {
                    viewModel.shareFlow
                        .collect {
                            delay(1000)
                            Logger.i("消费值：${it}")

                        }
                }
            }

            btnStateflow.setOnThrottleTimeClick {
                /*更新stateflow的值*/
                viewModel.changeStateFlow()
            }


            btnStateflowCollect.setOnThrottleTimeClick {
                /*新的stateflow订阅者*/
                viewModel.stateFlow.launchCollect(viewLifecycleOwner) {
                    Logger.i("stateflow newCollect:${it}")
                }
            }


            etSearch.debounce()
                .distinctUntilChanged()
                .onEach {
                    delay(2000)
                    Logger.i("输入框变更:${it}")
                }.launchIn(lifecycleScope)


        }
    }


    override fun observeViewModel() {

        viewModel.run {
//            stateFlow.launchCollect(viewLifecycleOwner) {
//                Logger.i("stateFlow collect:${it}")
//            }
//
//
//            lifecycleScope.launchSafety {
//                stateFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle)
//                    .collectLatest {
//                        Logger.i("stateFlow collect:${it}")
//                    }
//            }
//

            stateFlow.asLiveData()
                .observe(viewLifecycleOwner) {
                    Logger.i("stateFlow asLiveData observe:${it}")
                }

        }
    }


}