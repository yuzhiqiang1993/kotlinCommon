package com.yzq.kotlincommon.ui.activity

import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityAutodisposeBinding
import com.yzq.lib_base.ui.activity.BaseViewBindingActivity
import com.yzq.lib_rx.transform
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


/**
 * @description: AutoDispose
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:37
 */

@Route(path = RoutePath.Main.AUTODISPOSE)
class AutodisposeActivity : BaseViewBindingActivity<ActivityAutodisposeBinding>() {


    override fun getViewBinding() = ActivityAutodisposeBinding.inflate(layoutInflater)


    override fun initWidget() {


        initToolbar(binding.layoutToolbar.toolbar, "AutoDispose")

        binding.btnCount.setOnClickListener {
            startCount()

        }


    }


    private fun startCount() {
        Observable.interval(1, TimeUnit.SECONDS)
            .transform(this)
            .subscribe {
                binding.tvCount.text = "$it"
                LogUtils.i("startCount-->$it")
            }
    }


}
