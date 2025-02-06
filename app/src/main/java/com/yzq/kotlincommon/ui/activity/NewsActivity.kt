package com.yzq.kotlincommon.ui.activity

import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import coil.load
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.base.ui.img_pre.ImgPreviewActivity
import com.yzq.binding.viewBinding
import com.yzq.common.constants.RoutePath
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.coroutine.ext.launchSafety
import com.yzq.data.juhe.toutiao.TouTiao
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityMovieListBinding
import com.yzq.kotlincommon.databinding.ItemMovieLayoutBinding
import com.yzq.logger.Logger

/**
 * @description: 接口请求展示列表
 * @author : yzq
 * @date : 2019/4/30
 * @time : 13:40
 *
 */

@Route(path = RoutePath.Main.MOVIES)
class NewsActivity : BaseActivity() {
    private val binding by viewBinding(ActivityMovieListBinding::inflate)

    override fun initWidget() {

        initToolbar(binding.layoutToolbar.toolbar, "电影列表")

        binding.recy.linear().divider(R.drawable.item_divider)
            .setup {
                addType<TouTiao.Result.Data>(R.layout.item_movie_layout)
                onBind {
                    val itemMovieLayoutBinding = getBinding<ItemMovieLayoutBinding>()
                    val model = getModel<TouTiao.Result.Data>()
                    itemMovieLayoutBinding.ivImg.load(model.thumbnailPicS)
                    itemMovieLayoutBinding.tvTitle.text = model.title
                }

                R.id.iv_img.onClick {
                    val model = getModel<TouTiao.Result.Data>()
                    val itemMovieLayoutBinding = getBinding<ItemMovieLayoutBinding>()
                    val options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this@NewsActivity,
                            itemMovieLayoutBinding.ivImg,
                            getString(com.yzq.base.R.string.img_transition)
                        )

                    ImgPreviewActivity.start(
                        this@NewsActivity,
                        arrayListOf<String>(model.thumbnailPicS).apply {
                            add("http://gips3.baidu.com/it/u=3886271102,3123389489&fm=3028&app=3028&f=JPEG&fmt=auto?w=1280&h=960")
                        },
                        0,
                        options
                    )
                }
            }

        binding.layoutState.onRefresh {
            Logger.i("onRefresh")
            requestData()
        }.showLoading()
    }

    private fun requestData() {
        lifecycleScope.launchSafety {
            val listToutiao = RetrofitFactory.instance.getService(ApiService::class.java)
                .listToutiao(page = 1, pageSize = 50)
            showData(listToutiao)
        }.catch {
            binding.layoutState.showError(it)
        }
    }

    private fun showData(data: TouTiao?) {

        binding.run {
            if (data == null) {
                layoutState.showEmpty()
            } else {
                if (data.errorCode == 0) {
                    recy.bindingAdapter.models = data.result?.data
                    layoutState.showContent()
                } else {
                    layoutState.showError("${data.errorCode}--${data.reason}")
                }
            }
        }
    }
}
