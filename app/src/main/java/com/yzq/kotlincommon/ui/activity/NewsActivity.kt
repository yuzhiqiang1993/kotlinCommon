package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.api.ApiResult
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.juhe.toutiao.TouTiao
import com.yzq.common.ext.apiCall
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.coroutine.scope.lifeScope
import com.yzq.img.loadWithThumbnail
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityMovieListBinding
import com.yzq.kotlincommon.databinding.ItemMovieLayoutBinding

/**
 * @description: 接口请求展示列表
 * @author : yzq
 * @date : 2019/4/30
 * @time : 13:40
 *
 */

@Route(path = RoutePath.Main.MOVIES)
class NewsActivity : BaseActivity() {
    private val binding by viewbind(ActivityMovieListBinding::inflate)

    override fun initWidget() {

        initToolbar(binding.layoutToolbar.toolbar, "电影列表")

        binding.recy.linear().divider(R.drawable.item_divider)
            .setup {
                addType<TouTiao.Result.Data>(R.layout.item_movie_layout)
                onBind {
                    val itemMovieLayoutBinding = getBinding<ItemMovieLayoutBinding>()
                    val model = getModel<TouTiao.Result.Data>()
                    itemMovieLayoutBinding.ivImg.loadWithThumbnail(model.thumbnailPicS)
                    itemMovieLayoutBinding.tvTitle.setText(model.title)
                }

                R.id.iv_img.onClick {
                    val model = getModel<TouTiao.Result.Data>()
                    val itemMovieLayoutBinding = getBinding<ItemMovieLayoutBinding>()
                    preViewImg(model.thumbnailPicS, itemMovieLayoutBinding.ivImg)
                }
            }

        stateViewManager.initStateView(binding.stateView, binding.recy)
        binding.stateView.retry {
            initData()
        }
    }

    override fun initData() {
        requestData()
    }

    private fun requestData() {

        lifeScope {
            val apiResult = apiCall {
                RetrofitFactory.instance.getService(ApiService::class.java)
                    .listToutiao(page = 1, pageSize = 50)
            }

            when (apiResult) {
                is ApiResult.Error -> {
                    stateViewManager.showError(apiResult.message)
                }
                is ApiResult.Exception -> {
                    stateViewManager.showError(apiResult.t.message!!)
                }
                is ApiResult.Success -> {
                    showData(apiResult.data)
                }
            }
        }
    }

    private fun showData(data: TouTiao?) {

        if (data == null) {
            stateViewManager.showNoData()
        } else {
            binding.recy.bindingAdapter.models = data.result.data

            stateViewManager.showContent()
        }
    }
}
