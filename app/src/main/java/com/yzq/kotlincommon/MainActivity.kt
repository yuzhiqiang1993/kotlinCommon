package com.yzq.kotlincommon

import com.blankj.utilcode.util.LogUtils
import com.yzq.common.extend.dataConvert
import com.yzq.common.extend.transform
import com.yzq.common.mvp.view.BaseView
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.rx.BaseObserver
import com.yzq.common.ui.BaseActivity
import com.yzq.common.widget.StateView
import com.yzq.kotlincommon.api.ApiService
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



        doRequest()




        initToolbar(toolbar, "测试")
        stateView.setRetryListener(object : StateView.RetryListener {
            override fun retry() {
                RetrofitFactory.setBaseUrl("192.138.1.2/")
                doRequest()

            }

        })
    }

    private fun doRequest() {

        RetrofitFactory.instance.getService(ApiService::class.java).getData("xuncha02").dataConvert().transform(this)
                .subscribe(object : BaseObserver<String>(this) {
                    override fun onNext(t: String) {

                        LogUtils.i("返回的数据：" + t)
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
