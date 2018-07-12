package com.yzq.kotlincommon

import com.yzq.common.extend.transform
import com.yzq.common.mvp.view.BaseView
import com.yzq.common.rx.BaseObserver
import com.yzq.common.ui.BaseActivity
import com.yzq.common.widget.StateView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity(), BaseView {


    override fun getContentLayoutId(): Int {

        return R.layout.activity_main
    }


    override fun initWidget() {
        super.initWidget()


        initToolbar(toolbar, "测试")
        stateView.setRetryListener(object : StateView.RetryListener {
            override fun retry() {
                stateView.showNoNet()
            }

        })
    }

    override fun initData() {
        super.initData()


        Observable.timer(2, TimeUnit.SECONDS)
                .transform(this)
                .subscribe(object : BaseObserver<Long>(this) {
                    override fun onNext(t: Long) {

                        stateView.showNoData()
                    }

                })
    }

}
