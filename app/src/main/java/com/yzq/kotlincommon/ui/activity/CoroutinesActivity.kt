package com.yzq.kotlincommon.ui.activity

import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.blankj.utilcode.util.LogUtils
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.observeUIState
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.coroutine.flow.launchCollect
import com.yzq.coroutine.safety_coroutine.launchSafety
import com.yzq.coroutine.safety_coroutine.withDefault
import com.yzq.coroutine.safety_coroutine.withIO
import com.yzq.coroutine.safety_coroutine.withUnconfined
import com.yzq.kotlincommon.databinding.ActivityCoroutinesBinding
import com.yzq.kotlincommon.view_model.CoroutineViewModel
import kotlinx.coroutines.flow.filter

@Route(path = RoutePath.Main.COROUTINE)
class CoroutinesActivity : BaseActivity() {

    private val vm: CoroutineViewModel by viewModels()

    private val binding by viewbind(ActivityCoroutinesBinding::inflate)

    override fun initWidget() {
        super.initWidget()

        initToolbar(binding.includedToolbar.toolbar, "Coroutine 协程")

        binding.layoutState.onRefresh { vm.requestData() }.showLoading()

        lifecycleScope.launchSafety {
            LogUtils.i("launch 当前线程:${Thread.currentThread().name}")
        }

        lifecycleScope.launchSafety {
            withIO {
                LogUtils.i("IO 当前线程:${Thread.currentThread().name}")
            }
        }

        lifecycleScope.launchSafety {
            withDefault {
                LogUtils.i("Default 当前线程:${Thread.currentThread().name}")
            }
        }

        lifecycleScope.launchSafety {
            withUnconfined {
                LogUtils.i("Unconfined 当前线程:${Thread.currentThread().name}")
            }
        }

        lifecycleScope.launchSafety {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                LogUtils.i("lifecycleScope whenCreated")
            }
        }
    }

    override fun observeViewModel() {
        observeUIState(vm, stateLayout = binding.layoutState)
        vm.run {
            geocoder.observe(
                this@CoroutinesActivity,
                Observer {
                    /*liveData默认就支持页面在onStop时停止观察数据，onStart时观察数据*/
                    LogUtils.i("liveData请求完成")
                    binding.tv.text = it.result.formatted_address
                }
            )


            geocoderFlow
                .filter { it != null }
                .launchCollect(this@CoroutinesActivity) { // 扩展方法 在onStop的时候停止收集数据，onStart后再收集
                    LogUtils.i("stateFlow请求完成")
                    binding.tv.text = it!!.result.formatted_address
                }
        }

        /**
         * 监听stateFlow
         * 1.collect 是一个挂起函数，必须运行在协程作用域中
         * 2.要保证页面可见时再响应数据
         */
//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                vm.geocoderFlow.filter {
//                    it != null
//                }.collect {
//                    binding.tv.text = it!!.result.formatted_address
//                    stateViewManager.showContent()
//                }
//            }
//        }
    }
}
