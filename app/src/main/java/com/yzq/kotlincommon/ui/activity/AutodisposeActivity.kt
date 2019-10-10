package com.yzq.kotlincommon.ui.activity

import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.lib_base.ui.BaseActivity
import com.yzq.lib_rx.transform
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_autodispose.*
import java.util.concurrent.TimeUnit


/**
 * @description: AutoDispose
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:37
 *
 */

@Route(path = RoutePath.Main.AUTODISPOSE)
class AutodisposeActivity : BaseActivity() {
    override fun getContentLayoutId(): Int {

        return R.layout.activity_autodispose
    }

    override fun initWidget() {
        super.initWidget()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "AutoDispose")

        btn_count.setOnClickListener {
            startCount()
        }
    }



    private fun startCount() {
        Observable.interval(1, TimeUnit.SECONDS)
                .transform(this)
                .subscribe {
                    tv_count.setText("$it")
                    LogUtils.i("startCount-->$it")
                }
    }


}
