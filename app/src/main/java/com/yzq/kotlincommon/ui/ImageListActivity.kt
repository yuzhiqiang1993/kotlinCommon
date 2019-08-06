package com.yzq.kotlincommon.ui

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzq.common.constants.HttpRequestType
import com.yzq.common.constants.RoutePath
import com.yzq.common.ui.BaseMvvmActivity
import com.yzq.common.widget.AdapterLoadMoreView
import com.yzq.common.widget.StateView
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.ImgListAdapter
import com.yzq.kotlincommon.data.Subject
import com.yzq.kotlincommon.mvvm.view_model.ImgListViewModel
import kotlinx.android.synthetic.main.activity_image_list.*


/**
 * @description: 图片瀑布流
 * @author : yzq
 * @date   : 2019/5/23
 * @time   : 13:35
 *
 */

@Route(path = RoutePath.Main.IMG_LIST)
class ImageListActivity : BaseMvvmActivity<ImgListViewModel>(), BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    override fun setViewModel(): Class<ImgListViewModel> = ImgListViewModel::class.java


    private var imgListAdapter: ImgListAdapter = ImgListAdapter(R.layout.item_img_list, arrayListOf())


    var httpRequestType = HttpRequestType.FIRST


    override fun getContentLayoutId(): Int {

        return R.layout.activity_image_list
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

        showLoadding()
        viewModel.getData(HttpRequestType.FIRST)


        viewModel.subjectsLive.observe(this, object : Observer<List<Subject>> {
            override fun onChanged(t: List<Subject>) {
                requestSuccess(t)

            }

        })

    }


    fun requestSuccess(subjects: List<Subject>) {

        if (subjects.size == 0) {
            imgListAdapter.loadMoreEnd()
            return
        }

        if (httpRequestType == HttpRequestType.LOAD_MORE) {
            imgListAdapter.addData(subjects)
            imgListAdapter.loadMoreComplete()
        } else {
            imgListAdapter.setNewData(subjects)
        }

        showContent()

    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

        val imgView = recy.layoutManager!!.findViewByPosition(position)!!.findViewById<AppCompatImageView>(R.id.iv_img)
        preViewImg(imgListAdapter.data[position].images.large, imgView)

    }


    override fun onLoadMoreRequested() {

        if (viewModel.start <= 250) {

            viewModel.getData(HttpRequestType.LOAD_MORE)
        } else {
            imgListAdapter.loadMoreEnd()
        }


    }

}
