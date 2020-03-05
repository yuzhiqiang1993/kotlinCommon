package com.yzq.kotlincommon.ui.activity

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.movie.Subject
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.ImgListAdapter
import com.yzq.kotlincommon.mvvm.view_model.ImgListViewModel
import com.yzq.lib_base.extend.init
import com.yzq.lib_base.ui.BaseMvvmActivity
import com.yzq.lib_base_adapter.AdapterLoadMoreView
import kotlinx.android.synthetic.main.activity_image_list.*


/**
 * @description: 图片瀑布流
 * @author : yzq
 * @date   : 2019/5/23
 * @time   : 13:35
 *
 */

@Route(path = RoutePath.Main.IMG_LIST)
class ImageListActivity : BaseMvvmActivity<ImgListViewModel>(),
    OnItemClickListener, OnLoadMoreListener {


    override fun getViewModelClass(): Class<ImgListViewModel> = ImgListViewModel::class.java


    private var imgListAdapter = ImgListAdapter(R.layout.item_img_list, arrayListOf())


    override fun getContentLayoutId(): Int {

        return R.layout.activity_image_list
    }


    override fun initWidget() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "瀑布流图片（图片列表优化）")

        initRecy()


        initStateView(state_view, layout_swipe_refresh, true)

        state_view.retry {
            initData()
        }


        layout_swipe_refresh.setOnRefreshListener {

            requestType = httpRefresh

            initData()
        }
    }


    private fun initRecy() {

        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        //   val layoutManager = GridLayoutManager(this, 2)
        /*防止回到顶部时重新布局可能导致item跳跃*/
        //layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE

        recy.init(layoutManager, false)


        imgListAdapter.setOnItemClickListener(this)

        imgListAdapter.loadMoreModule?.setOnLoadMoreListener(this)
        imgListAdapter.loadMoreModule?.loadMoreView = AdapterLoadMoreView()


        recy.adapter = imgListAdapter


    }

    override fun observeViewModel() {

        with(vm) {
            subjectsLive.observe(this@ImageListActivity, Observer {

                handleDataChanged(it)
            })

            subjectsDiffResult.observe(this@ImageListActivity, Observer {

                LogUtils.i("更新数据")
                imgListAdapter.setDiffNewData(it, vm.subjectsLive.value!!)
            })

        }


    }


    override fun initData() {

        if (requestType == httpFirst) {

            showLoadding()
        }

        vm.start = 0

        vm.getData()


    }

    private fun handleDataChanged(t: List<Subject>) {

        if (requestType == httpLoadMore) {

            if (t.isEmpty()) {
                imgListAdapter.loadMoreModule?.loadMoreEnd()
            } else {
                imgListAdapter.addData(t)
                imgListAdapter.loadMoreModule?.loadMoreComplete()
            }


        } else {
            /*异步计算出需要更新的数据*/
            vm.calculateDiff(imgListAdapter.data, t)
        }

        showContent()


    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

        val imgView =
            recy.layoutManager!!.findViewByPosition(position)!!
                .findViewById<AppCompatImageView>(R.id.iv_img)
        preViewImg(imgListAdapter.data[position].images.large, imgView)

    }


    override fun onLoadMore() {
        LogUtils.i("onLoadMoreRequested")
        if (vm.start <= 250) {
            requestType = httpLoadMore
            vm.getData()
        } else {
            imgListAdapter.loadMoreModule?.loadMoreEnd()
        }


    }


}
