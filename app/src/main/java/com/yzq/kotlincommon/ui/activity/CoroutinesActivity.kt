package com.yzq.kotlincommon.ui.activity

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.mvvm.view_model.CoroutineViewModel
import com.yzq.lib_base.ui.BaseMvvmActivity
import kotlinx.android.synthetic.main.activity_coroutines.*


@Route(path = RoutePath.Main.COROUTINE)
class CoroutinesActivity : BaseMvvmActivity<CoroutineViewModel>() {

    override fun getViewModelClass(): Class<CoroutineViewModel> = CoroutineViewModel::class.java

    override fun getContentLayoutId(): Int = R.layout.activity_coroutines


    override fun initWidget() {
        super.initWidget()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        initToolbar(toolbar, "Coroutine 协程")
        initStateView(state_view, tv)
    }


    override fun initData() {
        super.initData()
        showLoadding()
        vm.requestData()


    }


    override fun observeViewModel() {


        with(vm) {
            subjects.observe(this@CoroutinesActivity, Observer {


                LogUtils.i("请求完成")
                tv.text = it[0].title

                showContent()

            })
        }

    }


}

