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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


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

            btnStart.setOnThrottleTimeClick {
                collect()
            }
        }
    }

    private fun collect() {
        lifecycleScope.launchSafety {
            viewModel.stringFlow
                .filterNotNull()
                .map {
                    /*中间操作符，还有很多其他的，用于处理数据*/
                    "${it}:map"
                }
                .flowOn(Dispatchers.IO)//flowOn只会影响上游的线程，后续会自动切到原本的线程
                .onStart {
                    /*发送数据前执行*/
                    LogUtils.i("onStart")
                }.onEmpty {
                    /*当什么数据也没发送的时候执行*/
                    LogUtils.i("onEmpty")
                }.onEach {
                    /*上次每次emit，这里都会执行*/
                    LogUtils.i("onEach:${it}")
                }.catch { t ->
                    /*只能捕获上游的异常 catch执行后collect是不会执行的，可以在这里面再次emit让collect得到执行*/
                    LogUtils.i("catch")
                    t.printStackTrace()
                    emit("异常了")

                }.onCompletion { t ->
                    /*执行完毕时执行*/
                    t?.printStackTrace()
                    LogUtils.i("onCompletion")
                }.collect {
                    /*终端操作符，触发数据流开始运行，是挂起函数，所以只能在协程中执行*/
                    LogUtils.i("collect:${it}")
                }
        }

    }

    override fun initData() {
        super.initData()


    }


}