package com.yzq.kotlincommon.ui

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzq.common.constants.HttpRequestType
import com.yzq.common.constants.RoutePath
import com.yzq.common.ui.BaseMvpActivity
import com.yzq.common.widget.AdapterLoadMoreView
import com.yzq.common.widget.StateView
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.ImgListAdapter
import com.yzq.kotlincommon.dagger.DaggerMainComponent
import com.yzq.kotlincommon.data.BaiDuImgBean
import com.yzq.kotlincommon.mvp.presenter.ImgListPresenter
import com.yzq.kotlincommon.mvp.view.ImgListView
import kotlinx.android.synthetic.main.activity_image_list.*


/**
 * @description: 图片瀑布流
 * @author : yzq
 * @date   : 2019/5/23
 * @time   : 13:35
 *
 */

@Route(path = RoutePath.Main.IMG_LIST)
class ImageListActivity : BaseMvpActivity<ImgListView, ImgListPresenter>(), ImgListView, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private val baiDuImgList = arrayListOf<BaiDuImgBean.Data>()

    private var imgListAdapter: ImgListAdapter = ImgListAdapter(R.layout.item_img_list, baiDuImgList)

    var httpRequestType = HttpRequestType.FIRST


    /*当前页码*/
    private var currentPage = 0
    /*总页码*/
    private var totalPage = 0
    /*每页请求多少条数据*/
    private val pageSize = 100

    override fun getContentLayoutId(): Int {

        return R.layout.activity_image_list
    }

    override fun initInject() {
        DaggerMainComponent.builder().build().inject(this)
    }

    override fun initWidget() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "瀑布流图片（图片列表优化）")

        initRecy()


        initStateView(state_view, layout_swipe_refresh, true)

        state_view.setRetryListener(object : StateView.RetryListener {
            override fun retry() {
                initData()
            }

        })


        layout_swipe_refresh.setOnRefreshListener {

            httpRequestType = HttpRequestType.REFRESH
            initData()
        }
    }

    private fun initRecy() {

        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        //   val layoutManager = GridLayoutManager(this, 2)
        /*防止回到顶部时重新布局可能导致item跳跃*/
        //layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE

        initRecycleView(recy, layoutManager = layoutManager, needItemDecoration = false)

        imgListAdapter.setOnItemClickListener(this)

        imgListAdapter.setEnableLoadMore(true)
//        imgListAdapter.openLoadAnimation()
//        imgListAdapter.isFirstOnly(false)
        // imgListAdapter.setPreLoadNumber(6)
        imgListAdapter.setOnLoadMoreListener(this, recy)
        imgListAdapter.setLoadMoreView(AdapterLoadMoreView)
        recy.adapter = imgListAdapter

    }


    override fun initData() {

        baiDuImgList.clear()

        if (httpRequestType == HttpRequestType.FIRST) {
            showLoadding()
        }

        presenter.getImgs(currentPage, pageSize)


    }

    override fun requestSuccess(baiDuImgBean: BaiDuImgBean) {

        val totalPageSize: Double = baiDuImgBean.totalNum / pageSize.toDouble()

        LogUtils.i("算出来的总页数：${totalPageSize}")

        totalPage = Math.ceil(totalPageSize).toInt()

        baiDuImgBean.data.remove(baiDuImgBean.data.last())


        if (httpRequestType == HttpRequestType.LOAD_MORE) {
            imgListAdapter.addData(baiDuImgBean.data)
            imgListAdapter.loadMoreComplete()
        } else {
            imgListAdapter.setNewData(baiDuImgBean.data)
        }


        showContent()

    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

        preViewImg(imgListAdapter.data[position].imageUrl)

    }


    override fun onLoadMoreRequested() {

        if (currentPage < totalPage) {
            httpRequestType = HttpRequestType.LOAD_MORE

            presenter.getImgs(currentPage, pageSize)
        } else {
            imgListAdapter.loadMoreEnd()
        }


    }

}
