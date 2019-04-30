package com.yzq.kotlincommon.ui

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzq.common.constants.RoutePath
import com.yzq.common.net.GsonConvert
import com.yzq.common.ui.BaseMvpActivity
import com.yzq.common.widget.ItemDecoration
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.NewsAdapter
import com.yzq.kotlincommon.dagger.DaggerMainComponent
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.mvp.presenter.MainPresenter
import com.yzq.kotlincommon.mvp.view.MainView
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.content_news.*


 
 /**
 * @description: 新闻页面
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:40
 * 
 */
 
@Route(path = RoutePath.Main.NEWS)
class NewsActivity : BaseMvpActivity<MainView, MainPresenter>(), MainView, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {


    private lateinit var newsAdapter: NewsAdapter
    private lateinit var operationItem: NewsBean.Data

    override fun initInject() {

        DaggerMainComponent.builder().build().inject(this)

    }

    override fun getContentLayoutId(): Int {
        return R.layout.activity_news
    }


    override fun initWidget() {
        super.initWidget()
        initToolbar(toolbar, "新闻")
        recy.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        initStateView(stateView, recy)

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

        newsAdapter = NewsAdapter(R.layout.item_news_layout, data)
        recy.addItemDecoration(ItemDecoration.baseItemDecoration(this))
        recy.adapter = newsAdapter
        newsAdapter.onItemClickListener = this
        newsAdapter.onItemChildClickListener = this


        showContent()
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {

        val data = GsonConvert.toJson(adapter.data.get(position))
        //LogUtils.i(data)
    }


    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

        operationItem = newsAdapter.data.get(position)

        when (view!!.id) {
            R.id.imgIv ->
                preViewImg(operationItem.title, operationItem.thumbnailPicS)
        }

    }


}
