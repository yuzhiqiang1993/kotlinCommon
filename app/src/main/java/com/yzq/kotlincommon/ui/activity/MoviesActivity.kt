package com.yzq.kotlincommon.ui.activity

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.movie.Subject
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.MovieAdapter
import com.yzq.kotlincommon.databinding.ActivityMovieListBinding
import com.yzq.kotlincommon.mvvm.view_model.MovieViewModel
import com.yzq.lib_base.extend.init
import com.yzq.lib_base.ui.BaseVbVmActivity


/**
 * @description: 电影列表页面
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:40
 *
 */

@Route(path = RoutePath.Main.MOVIES)
class MoviesActivity : BaseVbVmActivity<ActivityMovieListBinding, MovieViewModel>(),
    OnItemClickListener,
    OnItemChildClickListener {

    override fun getViewBinding() = ActivityMovieListBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MovieViewModel> = MovieViewModel::class.java


    private lateinit var movieAdapter: MovieAdapter
    private lateinit var operationItem: Subject


    override fun initWidget() {

        initToolbar(binding.layoutToolbar.toolbar, "电影列表")
        binding.recy.init()
        initStateView(binding.stateView, binding.recy)
        binding.stateView.retry {
            initData()
        }
    }


    override fun initData() {
        vm.requestData()
    }


    override fun observeViewModel() {
        vm.subjects.observe(this,
            Observer { t ->
                LogUtils.i("数据发生变化")

                if (t.isNotEmpty()) {
                    showData(t)
                } else {
                    showNoData()
                }
            })

    }


    private fun showData(data: MutableList<Subject>) {

        movieAdapter = MovieAdapter(R.layout.item_movie_layout, data)
        movieAdapter.addChildClickViewIds(R.id.iv_img)
        binding.recy.adapter = movieAdapter
        movieAdapter.setOnItemClickListener(this)
        movieAdapter.setOnItemChildClickListener(this)
        showContent()
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        operationItem = movieAdapter.data[position]

        ToastUtils.showShort(operationItem.title)
    }


    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

        operationItem = movieAdapter.data[position]
        val imgView =
            binding.recy.layoutManager!!.findViewByPosition(position)!!
                .findViewById<AppCompatImageView>(R.id.iv_img)
        when (view.id) {
            R.id.iv_img -> {
                preViewImg(operationItem.images.large, imgView)
            }
        }

    }


}
