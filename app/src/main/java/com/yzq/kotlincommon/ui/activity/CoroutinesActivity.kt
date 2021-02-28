package com.yzq.kotlincommon.ui.activity

import androidx.lifecycle.*
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityCoroutinesBinding
import com.yzq.kotlincommon.mvvm.view_model.CoroutineViewModel
import com.yzq.lib_base.ui.activity.BaseVbVmActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Route(path = RoutePath.Main.COROUTINE)
class CoroutinesActivity : BaseVbVmActivity<ActivityCoroutinesBinding, CoroutineViewModel>() {

    override fun getViewModelClass(): Class<CoroutineViewModel> = CoroutineViewModel::class.java

    override fun getViewBinding() = ActivityCoroutinesBinding.inflate(layoutInflater)


    override fun initWidget() {
        super.initWidget()



        initToolbar(binding.layoutToolbar.toolbar, "Coroutine 协程")
        initStateView(binding.stateView, binding.tv)

        binding.stateView.retry {
            initData()
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

    override fun initData() {
        super.initData()
        showLoading()
        vm.requestData()



    }


    override fun observeViewModel() {


        with(vm) {
            geocoder.observe(this@CoroutinesActivity, Observer {

                LogUtils.i("请求完成")
                binding.tv.text = it.formattedAddress

                showContent()

            })
        }

    }


}

