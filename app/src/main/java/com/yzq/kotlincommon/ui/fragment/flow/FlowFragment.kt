package com.yzq.kotlincommon.ui.fragment.flow

import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.yzq.base.extend.observeUIState
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.fragment.BaseFragment
import com.yzq.binding.viewbind
import com.yzq.coroutine.scope.launchSafety
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.FragmentFlowBinding
import com.yzq.widget.dialog.BubbleDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn


/**
 * @description flow示例
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/12/28
 * @time    17:35
 */

class FlowFragment : BaseFragment(R.layout.fragment_flow) {

    private val loadingDialog by lazy { BubbleDialog(requireContext()) }

    companion object {
        fun newInstance() = FlowFragment()
    }

    private val viewModel: FlowViewModel by viewModels()

    private val binding by viewbind(FragmentFlowBinding::bind)


    override fun initWidget() {

        binding.run {


            btnCollect.setOnThrottleTimeClick {
                collect()
            }

            btnLaunchin.setOnThrottleTimeClick {
                /*launchIn表示在指定协程作用域中立刻执行*/
                viewModel.stringFlow.launchIn(lifecycleScope)
            }

            btnFirst.setOnThrottleTimeClick {
                lifecycleScope.launchSafety {
                    /*返回第一个后就会结束,不会等到flow走完*/
                    val first = viewModel.stringFlow.first()
                    LogUtils.i("first:${first}")
                }
            }

            btnLast.setOnThrottleTimeClick {
                lifecycleScope.launchSafety {
                    /*等flow走完，返回最后一个值*/
                    val last = viewModel.stringFlow.last()

                    LogUtils.i("last:${last}")

                    viewModel.stringFlow.collectLatest {
                        delay(3000)
//                        LogUtils.i("Collecting $it")
                        /*来不及处理的数据会被丢弃掉，可以用在输入框搜索的场景*/
                        LogUtils.i("collected:${it}")
                    }
                }
            }

            btnApiRequest.setOnThrottleTimeClick {

            }
        }
    }


    private fun collect() {

        /**
         * 这里使用launchCollect首次执行后，以后每次页面从后台切换到前台都会执行一次collect
         * 又由于stringFlow是冷流，每次collect都会执行一次数据流发送，所以这里在前后台切换时会发现多次执行
         * 优势在于页面处于后台时，会自动停止收集流，发送流的逻辑也会停止，下次页面处于可见时会再次执行
         */
//        viewModel.stringFlow
//            .launchCollect(this) {
//                LogUtils.i("collect:${it}")
//            }

        lifecycleScope.launchSafety {
            viewModel.stringFlow
                .collect {
                    /**
                     * 这种实现的缺点是flow收集数据(消费端)不具备生命周期感知能力，也就是说即使页面主语不可见状态，也会执行
                     */
                    LogUtils.i("collect:${it}")
                }
        }

    }

    override fun observeViewModel() {

        viewModel.run {
            observeUIState(this, loadingDialog)

            /**
             * 转成livedata后订阅数据更新,页面开启时会自动执行一次，后续只会在数据有变更时才会更新
             */
            userFlow.asLiveData()
                .observe(viewLifecycleOwner) {
                    LogUtils.i("userFlow asLiveData 请求结果:${it}")
                }

            /**
             * 页面打开时就会执行一些，且前后台切换都会重新执行
             */
//            userFlow.launchCollect(viewLifecycleOwner) {
//                LogUtils.i("userFlow 请求结果:${it}")
//            }
        }

    }
}