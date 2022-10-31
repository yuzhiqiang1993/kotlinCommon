package com.yzq.kotlincommon.ui.activity

import androidx.lifecycle.*
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.yzq.base.extend.launchCollect
import com.yzq.base.ui.activity.BaseVmActivity
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.api.ApiResult
import com.yzq.common.ext.apiCall
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.kotlincommon.databinding.ActivityCoroutinesBinding
import com.yzq.kotlincommon.mvvm.view_model.CoroutineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Route(path = RoutePath.Main.COROUTINE)
class CoroutinesActivity : BaseVmActivity<ActivityCoroutinesBinding, CoroutineViewModel>() {

    override fun getViewModelClass(): Class<CoroutineViewModel> = CoroutineViewModel::class.java

    override fun createBinding() = ActivityCoroutinesBinding.inflate(layoutInflater)
    override fun initWidget() {
        super.initWidget()



        initToolbar(binding.layoutToolbar.toolbar, "Coroutine 协程")
        stateViewManager.initStateView(binding.stateView, binding.tv)

        binding.stateView.retry {
            stateViewManager.switchToHttpFirst()

//            requestData()
            vm.requestData()

        }


        launch {
            LogUtils.i("launch 当前线程:${Thread.currentThread().name}")
        }

        launch(Dispatchers.IO) {
            LogUtils.i("IO 当前线程:${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) {
            LogUtils.i("Default 当前线程:${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) {
            LogUtils.i("Unconfined 当前线程:${Thread.currentThread().name}")
        }



        lifecycleScope.launchWhenCreated {
            whenCreated {
                LogUtils.i("lifecycleScope whenCreated")
            }
            whenStarted {
                LogUtils.i("lifecycleScope whenStarted")
            }
            whenResumed {
                LogUtils.i("lifecycleScope whenResumed")
            }

        }
    }

    private fun requestData() {

        launch {
            val apiResult = apiCall {
                RetrofitFactory.instance.getService(ApiService::class.java).geocoder()
            }
            when (apiResult) {
                is ApiResult.Error -> {}
                is ApiResult.Exception -> {}
                is ApiResult.Success -> {
                    binding.tv.text = apiResult.data!!.result.formatted_address
                    stateViewManager.showContent()
                }
            }

//            apiResult.onSuccess {
//                binding.tv.text = it!!.result.formatted_address
//                stateViewManager.showContent()
//            }
//
//            apiResult.onFailed { code, message ->
//
//            }
//
//            apiResult.onException {
//
//            }
        }


    }

    override fun initData() {
        stateViewManager.switchToHttpFirst()
//        vm.requestData()
        requestData()
    }

    override fun observeViewModel() {

        vm.run {
            geocoder.observe(this@CoroutinesActivity, Observer {
                LogUtils.i("请求完成")
                binding.tv.text = it.result.formatted_address

                stateViewManager.showContent()
            })

            geocoderFlow.filter { it != null }
                .launchCollect(this@CoroutinesActivity) {//扩展方法
                    binding.tv.text = it!!.result.formatted_address
                    stateViewManager.showContent()
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

