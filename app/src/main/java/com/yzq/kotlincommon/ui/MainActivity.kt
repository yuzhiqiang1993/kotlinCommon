package com.yzq.kotlincommon.ui

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzq.common.net.GsonConvert
import com.yzq.common.ui.BaseMvpActivity
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.NewsAdapter
import com.yzq.kotlincommon.dagger.DaggerMainComponent
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.mvp.presenter.MainPresenter
import com.yzq.kotlincommon.mvp.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseMvpActivity<MainView, MainPresenter>(), MainView, BaseQuickAdapter.OnItemClickListener {


    private lateinit var adapter: NewsAdapter

    override fun initInject() {

        DaggerMainComponent.builder().build().inject(this)
    }


    override fun getContentLayoutId(): Int {

        return R.layout.activity_main
    }


    override fun initWidget() {
        super.initWidget()
        initToolbar(toolbar, "新闻")

        recy.layoutManager = LinearLayoutManager(this)

    }


    override fun initData() {
        super.initData()
        showLoadding()

        presenter.requestData()


    }


    override fun requestSuccess(data: List<NewsBean.Data>) {

        if (data.size > 0) {
            showData(data)
        } else {
            showContent()
        }


    }

    private fun showData(data: List<NewsBean.Data>) {

        adapter = NewsAdapter(R.layout.item_news_content, data)
        recy.adapter = adapter
        adapter.setOnItemClickListener(this)


        showContent()
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {


        var data = GsonConvert.toJson(adapter.data.get(position))
        LogUtils.i(data)
    }


    override fun showContent() {

        stateView.hide()
        recy.visibility = View.VISIBLE
    }

    override fun showLoadding() {

        stateView.showLoading()
        recy.visibility = View.GONE
    }

    override fun showError(msg: String) {
        stateView.showError(msg)
        recy.visibility = View.GONE
    }


}
