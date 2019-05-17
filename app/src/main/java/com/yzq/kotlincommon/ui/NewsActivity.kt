package com.yzq.kotlincommon.ui

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzq.common.constants.RoutePath
import com.yzq.common.ui.BaseMvpActivity
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.NewsAdapter
import com.yzq.kotlincommon.dagger.DaggerMainComponent
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.mvp.presenter.MainPresenter
import com.yzq.kotlincommon.mvp.view.MainView
import kotlinx.android.synthetic.main.activity_news.*


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
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "新闻")

        initLinearRecycleView(recy)

        initStateView(state_view, recy)

    }


    override fun initData() {
        super.initData()
        showLoadding()

        presenter.requestData()


//        GlobalScope.launch(Dispatchers.Main) {
//
//            try {
//                LogUtils.i("开始请求数据")
//
//                val data = presenter.getNews().await()
//
//                LogUtils.i("数据请求完成：${data.result.data}")
//                requestSuccess(data.result.data)
//                LogUtils.i("请求结束")
//            } catch (e: Exception) {
//                showError(e.localizedMessage)
//            }
//
//
//        }

    }


    override fun requestSuccess(data: List<NewsBean.Data>) {

        if (data.size > 0) {
            showData(data)
        } else {
            showNoData()
        }


    }

    private fun showData(data: List<NewsBean.Data>) {


        newsAdapter = NewsAdapter(R.layout.item_news_layout, data, this)
        recy.adapter = newsAdapter
        newsAdapter.onItemClickListener = this
        newsAdapter.onItemChildClickListener = this

        recy.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    /*当列表处于自动滚动的状态下,此时不加载图片*/

                    Glide.with(this@NewsActivity).pauseRequests()
                } else {
                    Glide.with(this@NewsActivity).resumeRequests()

                }
            }
        })



        showContent()
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        operationItem = newsAdapter.data.get(position)

        ToastUtils.showShort(operationItem.title)
    }


    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

        operationItem = newsAdapter.data.get(position)

        when (view!!.id) {
            R.id.iv_img ->
                preViewImg(operationItem.thumbnailPicS)
        }

    }


}
