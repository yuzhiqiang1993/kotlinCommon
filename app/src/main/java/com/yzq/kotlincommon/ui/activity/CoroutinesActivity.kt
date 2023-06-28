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
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.coroutine.flow.launchCollect
import com.yzq.coroutine.safety_coroutine.launchSafety
import com.yzq.coroutine.safety_coroutine.scope.LifeSafetyScope
import com.yzq.coroutine.safety_coroutine.withDefault
import com.yzq.coroutine.safety_coroutine.withIO
import com.yzq.coroutine.safety_coroutine.withUnconfined
import com.yzq.kotlincommon.databinding.ActivityCoroutinesBinding
import com.yzq.kotlincommon.view_model.CoroutineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

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
        lifecycleScope.launch {

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


        binding.btnCustomeScope.setOnThrottleTimeClick {
            /*具备自动取消且有异常兜底的协程作用域，可以随处使用，类似于上面官方提供的  MainScope().launch {} 这种用法*/
            LifeSafetyScope(this, Lifecycle.Event.ON_DESTROY, dispatcher = Dispatchers.IO)
                .launch {
                    LogUtils.i("LifeSafetyScope 开始执行")
                    1 / 0
                }.catch {
                    LogUtils.i("LifeSafetyScope 捕获到异常了")
                }.finally {
                    it?.let {
                        it.printStackTrace()
                    }
                    LogUtils.i("LifeSafetyScope 执行结束")
                }
        }

        binding.btnCustomeCoroutine.setOnThrottleTimeClick {

            lifecycleScope.launchSafety {
                LogUtils.i("launchSafety 开始执行")
//                1 / 0//测试异常捕获是否有效

                delay(1000)

                LogUtils.i("launchSafety 执行完成")

            }.catch {
                /*这里异常可能会出现捕获不到的情况，原因是catch是在block后面设置的，如果block中立刻抛出了异常，此时catch可能是空，因此，不会被捕获到*/
                LogUtils.i("launchSafety catch")
            }.finally {
                it?.let {
                    it.printStackTrace()
                }
                LogUtils.i("launchSafety finally")
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
                    binding.tv.text = it.toString()

                }
            )


            geocoderFlow
                .filter { it != null }
                .launchCollect(this@CoroutinesActivity) { // 扩展方法 在onStop的时候停止收集数据，onStart后再收集
                    LogUtils.i("stateFlow请求完成")
                    binding.tv.text = it.toString()
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
