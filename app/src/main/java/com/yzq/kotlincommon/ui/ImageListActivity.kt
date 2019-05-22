package com.yzq.kotlincommon.ui

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzq.common.constants.HttpRequestType
import com.yzq.common.constants.RoutePath
import com.yzq.common.ui.BaseMvpActivity
import com.yzq.common.widget.StateView
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.ImgListAdapter
import com.yzq.kotlincommon.dagger.DaggerMainComponent
import com.yzq.kotlincommon.data.BaiDuImgBean
import com.yzq.kotlincommon.mvp.presenter.ImgListPresenter
import com.yzq.kotlincommon.mvp.view.ImgListView
import kotlinx.android.synthetic.main.activity_image_list.*


@Route(path = RoutePath.Main.IMG_LIST)
class ImageListActivity : BaseMvpActivity<ImgListView, ImgListPresenter>(), ImgListView, BaseQuickAdapter.OnItemClickListener {


    private var imgListAdapter: ImgListAdapter? = null

    var httpRequestType = HttpRequestType.FIRST

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
            initData()
        }
    }

    private fun initRecy() {

        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        //  val layoutManager = GridLayoutManager(this, 3)
        /*防止回到顶部时重新布局可能导致item跳跃*/
        //layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        initRecycleView(recy, layoutManager, hasImg = true, needItemDecoration = false)


    }


    override fun initData() {

        if (httpRequestType == HttpRequestType.FIRST) {
            showLoadding()
        }

        presenter.getImgs()

    }

    override fun requestSuccess(data: ArrayList<BaiDuImgBean.Data>) {

        data.remove(data.last())

        if (imgListAdapter == null) {
            imgListAdapter = ImgListAdapter(R.layout.item_img_list, data, this)
            recy.adapter = imgListAdapter
            imgListAdapter!!.setOnItemClickListener(this)
        } else {
            imgListAdapter!!.setNewData(data)
        }


        showContent()

    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

        preViewImg(imgListAdapter!!.data[position].imageUrl)

    }

}
