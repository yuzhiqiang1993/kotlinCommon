package com.yzq.kotlincommon.ui

import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.common.extend.transform
import com.yzq.common.rx.RxSchedulers
import com.yzq.common.ui.BaseActivity
import com.yzq.kotlincommon.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_autodispose.*
import java.util.concurrent.TimeUnit

/*
* Autodispose示例
*
*
* */
@Route(path = RoutePath.Main.AUTODISPOSE)
class AutodisposeActivity : BaseActivity() {
    override fun getContentLayoutId(): Int {

        return R.layout.activity_autodispose
    }

    override fun initWidget() {
        super.initWidget()
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "Autodispose")

        countBtn.setOnClickListener {
            startCount()

        }
    }


    private fun startCount() {

        Observable.interval(1, TimeUnit.SECONDS)
                .compose(RxSchedulers.io2main())
                .transform(this)
                .subscribe {
                    countTv.setText("$it")
                    LogUtils.i("startCount-->$it")
                }


    }


}
