package com.yzq.kotlincommon.ui.activity

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.*
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.mvvm.view_model.CoroutineViewModel
import com.yzq.lib_base.ui.BaseMvvmActivity
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Route(path = RoutePath.Main.COROUTINE)
class CoroutinesActivity : BaseMvvmActivity<CoroutineViewModel>() {

    override fun getViewModelClass(): Class<CoroutineViewModel> = CoroutineViewModel::class.java

    override fun getContentLayoutId(): Int = R.layout.activity_coroutines


    override fun initWidget() {
        super.initWidget()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        initToolbar(toolbar, "Coroutine 协程")
        initStateView(state_view, tv)

        state_view.retry {
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
                tv.text = it.formattedAddress

                showContent()

            })
        }

    }


}

