package com.yzq.kotlincommon.ui.activity

import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.observeUIState
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.coroutine.ext.launchSafety
import com.yzq.coroutine.ext.withDefault
import com.yzq.coroutine.ext.withIO
import com.yzq.coroutine.flow.launchCollect
import com.yzq.coroutine.safety_coroutine.scope.LifeSafetyScope
import com.yzq.kotlincommon.databinding.ActivityCoroutinesBinding
import com.yzq.kotlincommon.view_model.CoroutineViewModel
import com.yzq.logger.Logger
import com.yzq.router.RoutePath
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@Route(path = RoutePath.Main.COROUTINE)
class CoroutinesActivity : BaseActivity() {

    private val vm: CoroutineViewModel by viewModels()

    private val binding by viewBinding(ActivityCoroutinesBinding::inflate)

    override fun initWidget() {
        super.initWidget()

        initToolbar(binding.includedToolbar.toolbar, "Coroutine 协程")

        binding.layoutState.onRefresh { vm.requestData() }.showLoading()

        lifecycleScope.launchSafety {
            Logger.i("launch 当前线程:${Thread.currentThread().name}")
        }
        lifecycleScope.launch {

        }

        lifecycleScope.launchSafety {
            withIO {
                Logger.i("IO 当前线程:${Thread.currentThread().name}")
            }
        }

        lifecycleScope.launchSafety {
            withDefault {
                Logger.i("Default 当前线程:${Thread.currentThread().name}")
            }
        }


        lifecycleScope.launchSafety {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                Logger.i("lifecycleScope whenCreated")
            }
        }


        binding.btnCoroutine.setOnThrottleTimeClick {

            lifecycleScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                Logger.i("CoroutineExceptionHandler 捕获到异常了:${throwable.message}")
            }) {
                supervisorScope {
                    Logger.i("supervisorScope 开始执行")
                    launch {
                        Logger.i("supervisorScope launch 开始执行")
                        throw Exception("子协程内部出现异常")
                    }

                    delay(1000)
                    1 / 0
                    Logger.i("supervisorScope 执行结束")


                }

            }.invokeOnCompletion {
                it?.run {
                    Logger.i("异常:${it.message}")
                }
                Logger.i("协程 执行结束")
            }
        }


        binding.btnCustomeScope.setOnThrottleTimeClick {
            /*具备自动取消且有异常兜底的协程作用域，可以随处使用，类似于上面官方提供的  MainScope().launch {} 这种用法*/
            LifeSafetyScope(this, Lifecycle.Event.ON_DESTROY, dispatcher = Dispatchers.IO)
                .launch {
                    Logger.i("LifeSafetyScope 开始执行")
                    1 / 0
                }.catch {
                    Logger.i("LifeSafetyScope 捕获到异常了")
                }.finally {
                    it?.let {
                        it.printStackTrace()
                    }
                    Logger.i("LifeSafetyScope 执行结束")
                }
        }

        binding.btnCustomeCoroutine.setOnThrottleTimeClick {

            lifecycleScope.launchSafety {
                Logger.i("launchSafety 开始执行")
//                1 / 0//测试异常捕获是否有效

                supervisorScope {

                    launchSafety {
                        throw Exception("supervisorScope1 异常")
                    }.catch {
                        Logger.i("supervisorScope1 捕获到异常了")
                    }.finally {
                        it?.printStackTrace()
                        Logger.i("supervisorScope1 执行结束")
                    }


                    launchSafety {
                        throw Exception("supervisorScope2 异常")
                    }.catch {
                        Logger.i("supervisorScope2 捕获到异常了")
                    }.finally {
                        it?.printStackTrace()
                        Logger.i("supervisorScope2 执行结束")
                    }


                }.invokeOnCompletion {
                    Logger.i("supervisorScope 执行结束")
                }

                Logger.i("launchSafety 执行继续执行")
                1 / 0//测试异常捕获是否有效
                delay(1000)

                Logger.i("launchSafety 执行完成")

            }.catch {
                /*这里异常可能会出现捕获不到的情况，原因是catch是在block后面设置的，如果block中立刻抛出了异常，此时catch可能是空，因此，不会被捕获到*/
                Logger.i("launchSafety catch:${it.message}")
            }.finally {

                Logger.i("launchSafety finally ${it?.message}")
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
                    Logger.i("liveData请求完成")
                    binding.tv.text = it.toString()

                }
            )


            geocoderFlow
                .filter { it != null }
                .launchCollect(this@CoroutinesActivity) { // 扩展方法 在onStop的时候停止收集数据，onStart后再收集
                    Logger.i("stateFlow请求完成")
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
