package com.yzq.kotlincommon.ui.activity

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.coroutine.scope.launchSafety
import com.yzq.kotlincommon.databinding.ActivityFlowBinding
import com.yzq.kotlincommon.view_model.FlowViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn


/**
 * @description 协程中的flow
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/12/27
 * @time    09:54
 */

@Route(path = RoutePath.Main.FLOW)
class FlowActivity : BaseActivity() {

    private val binding by viewbind(ActivityFlowBinding::inflate)

    private val viewModel: FlowViewModel by viewModels()


    override fun initWidget() {
        super.initWidget()

        binding.run {
            initToolbar(includedToolbar.toolbar, "Flow")

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


        /**
         * launchIn表示在指定协程作用域中立刻执行flow，具体应用场景看代码注释
         */
//        viewModel.stringFlow.launchIn(lifecycleScope)

        lifecycleScope.launchSafety {
            viewModel.stringFlow
                .collect {
                    /*终端操作符，触发数据流开始运行，是挂起函数，所以只能在协程中执行*/
                    LogUtils.i("collect:${it}")
                }
        }

    }

    override fun initData() {
        super.initData()


    }


}