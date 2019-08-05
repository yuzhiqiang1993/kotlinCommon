package com.yzq.kotlincommon.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzq.common.constants.RoutePath
import com.yzq.common.ui.BaseMvvmActivity
import com.yzq.common.widget.StateView
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.MovieAdapter
import com.yzq.kotlincommon.data.Subject
import com.yzq.kotlincommon.mvp.view_model.MovieViewModel
import kotlinx.android.synthetic.main.activity_movie_list.*


/**
 * @description: 电影列表页面
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:40
 *
 */

@Route(path = RoutePath.Main.MOVIES)
class MoviesVmActivity : BaseMvvmActivity<MovieViewModel>(), BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {


    private lateinit var movieAdapter: MovieAdapter
    private lateinit var operationItem: Subject

    private var start = 0
    private var count = 50


    override fun onCreate(savedInstanceState: Bundle?) {

        initViewModel(MovieViewModel::class.java)
        super.onCreate(savedInstanceState)
    }


    override fun getContentLayoutId(): Int {


        return R.layout.activity_movie_list
    }


    override fun initWidget() {
        super.initWidget()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "电影列表")

        initRecycleView(recy)

        initStateView(state_view, recy)
        state_view.setRetryListener(object : StateView.RetryListener {
            override fun retry() {

                initData()
            }

        })

    }


    override fun initData() {
        super.initData()
        showLoadding()

        viewModel.requestData(start, count)


        viewModel.subjects.observe(this, object : Observer<List<Subject>> {
            override fun onChanged(t: List<Subject>) {

                LogUtils.i("数据发生变化")
                start = count
                count += count

                if (t.size > 0) {
                    showData(t)
                } else {
                    showNoData()
                }


            }
        })


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


    private fun showData(data: List<Subject>) {


        movieAdapter = MovieAdapter(R.layout.item_movie_layout, data)
        recy.adapter = movieAdapter
        movieAdapter.onItemClickListener = this
        movieAdapter.onItemChildClickListener = this

        showContent()
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        operationItem = movieAdapter.data.get(position)

        ToastUtils.showShort(operationItem.title)
    }


    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {

        operationItem = movieAdapter.data.get(position)
        val imgView = recy.layoutManager!!.findViewByPosition(position)!!.findViewById<AppCompatImageView>(R.id.iv_img)
        when (view!!.id) {
            R.id.iv_img -> {

                preViewImg(operationItem.images.large, imgView)
            }
        }

    }


}
