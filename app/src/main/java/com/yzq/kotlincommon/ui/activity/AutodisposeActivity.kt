package com.yzq.kotlincommon.ui.activity

import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityAutodisposeBinding
import com.yzq.lib_base.ui.BaseViewBindingActivity
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
        super.initWidget()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "AutoDispose")

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
