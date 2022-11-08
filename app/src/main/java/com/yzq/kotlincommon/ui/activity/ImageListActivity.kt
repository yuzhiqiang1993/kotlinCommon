package com.yzq.kotlincommon.ui.activity

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.yzq.base.extend.init
import com.yzq.base.ui.activity.BaseVmActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.movie.Subject
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.ImgListAdapter
import com.yzq.kotlincommon.databinding.ActivityImageListBinding
import com.yzq.kotlincommon.view_model.ImgListViewModel
import com.yzq.recycleview_adapter.AdapterLoadMoreView


/**
 * @description: 图片瀑布流
 * @author : yzq
 * @date   : 2019/5/23
 * @time   : 13:35
 *
 */

@Route(path = RoutePath.Main.IMG_LIST)
class ImageListActivity : BaseVmActivity<ImgListViewModel>(),
    OnItemClickListener, OnLoadMoreListener {
    private val binding by viewbind(ActivityImageListBinding::inflate)

    override fun getViewModelClass(): Class<ImgListViewModel> = ImgListViewModel::class.java


    private var imgListAdapter = ImgListAdapter(R.layout.item_img_list, arrayListOf())


    override fun initWidget() {

        initToolbar(binding.layoutToolbar.toolbar, "瀑布流图片（图片列表优化）")

        initRecy()


        stateViewManager.initStateView(binding.stateView, binding.layoutSwipeRefresh)

        binding.stateView.retry {
            initData()
        }


        binding.layoutSwipeRefresh.setOnRefreshListener {

            stateViewManager.switchToHttpRefresh()

            initData()
        }
    }


    private fun initRecy() {

        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        //   val layoutManager = GridLayoutManager(this, 2)
        /*防止回到顶部时重新布局可能导致item跳跃*/
        //layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE

        binding.recy.init(layoutManager, false)


        imgListAdapter.setOnItemClickListener(this)

        imgListAdapter.loadMoreModule.setOnLoadMoreListener(this)
        imgListAdapter.loadMoreModule.loadMoreView = AdapterLoadMoreView()


        binding.recy.adapter = imgListAdapter


    }

    override fun observeViewModel() {

        vm.run {
            subjectsLive.observe(this@ImageListActivity) {

                handleDataChanged(it)
            }

            subjectsDiffResult.observe(this@ImageListActivity) {

                LogUtils.i("更新数据")
                imgListAdapter.setDiffNewData(it, vm.subjectsLive.value!!)
            }

        }


    }


    override fun initData() {


        stateViewManager.switchToHttpFirst()

        vm.start = 0

        vm.getData()


    }

    private fun handleDataChanged(t: List<Subject>) {

        if (stateViewManager.isHttpLoadMore()) {

            if (t.isEmpty()) {
                imgListAdapter.loadMoreModule.loadMoreEnd()
            } else {
                imgListAdapter.addData(t)
                imgListAdapter.loadMoreModule.loadMoreComplete()
            }


        } else {
            /*异步计算出需要更新的数据*/
            vm.calculateDiff(imgListAdapter.data, t)
        }

        stateViewManager.showContent()


    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

        val imgView =
            binding.recy.layoutManager!!.findViewByPosition(position)!!
                .findViewById<AppCompatImageView>(R.id.iv_img)
        preViewImg(imgListAdapter.data[position].images.large, imgView)

    }


    override fun onLoadMore() {
        LogUtils.i("onLoadMoreRequested")
        if (vm.start <= 250) {
            stateViewManager.switchToHttpLoadMore()
            vm.getData()
        } else {
            imgListAdapter.loadMoreModule.loadMoreEnd()
        }


    }


}
