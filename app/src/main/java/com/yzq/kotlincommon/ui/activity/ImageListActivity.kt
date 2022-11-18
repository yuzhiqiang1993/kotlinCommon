package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.setup
import com.drake.brv.utils.staggered
import com.scwang.smart.refresh.layout.constant.RefreshState
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
import com.yzq.kotlincommon.databinding.ActivityImageListBinding
import com.yzq.kotlincommon.databinding.ItemImgListBinding
import kotlin.random.Random

/**
 * @description: 图片瀑布流
 * @author : yzq
 * @date : 2019/5/23
 * @time : 13:35
 *
 */

@Route(path = RoutePath.Main.IMG_LIST)
class ImageListActivity : BaseActivity() {

    private var page = 1
    private val pageSize = 30

    private val binding by viewbind(ActivityImageListBinding::inflate)

    override fun initWidget() {

        initToolbar(binding.includedToolbar.toolbar, "瀑布流图片（图片列表优化）")

        initRecy()

        binding.layoutPageRefresh.onRefresh {
            initData()
        }.autoRefresh()

        binding.layoutPageRefresh.onLoadMore {
            page += 1
            requestData()
        }
    }

    private fun initRecy() {

        binding.recy.staggered(3)
            .setup {
                addType<TouTiao.Result.Data>(R.layout.item_img_list)
                onBind {
                    val itemImgListBinding = getBinding<ItemImgListBinding>()
                    val model = getModel<TouTiao.Result.Data>()
                    /*高度随机*/
                    itemImgListBinding.ivImg.layoutParams.height = 300 + Random.nextInt(300)
                    itemImgListBinding.ivImg.loadWithThumbnail(model.thumbnailPicS)
                }
            }
    }

    override fun initData() {
        page = 1
        requestData()
    }

    private fun requestData() {

        lifeScope {
            val apiResult = apiCall {
                RetrofitFactory.instance.getService(ApiService::class.java)
                    .listToutiao(page = page, pageSize = pageSize)
            }

            when (apiResult) {
                is ApiResult.Error -> {
                    binding.layoutPageRefresh.showError(apiResult.message)
                }
                is ApiResult.Exception -> {
                    binding.layoutPageRefresh.showError(apiResult.t.message)
                }
                is ApiResult.Success -> {
                    setData(apiResult.data)
                }
            }
        }
    }

    private fun setData(data: TouTiao?) {
        data?.run {
            binding.run {
                if (layoutPageRefresh.state == RefreshState.Loading) {
                    recy.bindingAdapter.addModels(data.result.data)
                } else {
                    recy.bindingAdapter.setDifferModels(data.result.data)
                }

                layoutPageRefresh.showContent()
            }
        }
    }
}
