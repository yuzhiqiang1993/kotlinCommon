package com.yzq.kotlincommon.ui.activity

import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.setup
import com.drake.brv.utils.staggered
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.ImgPreviewActivity
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.juhe.toutiao.TouTiao
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.coroutine.scope.launchSafety
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
            page = 1
            requestData()
        }.autoRefresh()

        binding.layoutPageRefresh.onLoadMore {
            preloadIndex = 6
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

                R.id.iv_img.onClick {
                    val model = getModel<TouTiao.Result.Data>()
                    val itemMovieLayoutBinding = getBinding<ItemImgListBinding>()

                    val intent = Intent(this@ImageListActivity, ImgPreviewActivity::class.java)
                    intent.putExtra(ImgPreviewActivity.IMG_PATH, model.thumbnailPicS)

                    val options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this@ImageListActivity,
                            itemMovieLayoutBinding.ivImg,
                            getString(com.yzq.base.R.string.img_transition)
                        )
                    startActivity(intent, options.toBundle())
                }
            }
    }

//    override fun initData() {
//        page = 1
//        requestData()
//    }

    private fun requestData() {
        lifecycleScope.launchSafety {
            val listToutiao = RetrofitFactory.instance.getService(ApiService::class.java)
                .listToutiao(page = 1, pageSize = 50)
            setData(listToutiao)
        }.catch {
            binding.layoutPageRefresh.showError(it)
        }
    }

    private fun setData(data: TouTiao?) {

        if (data == null) {
            binding.layoutPageRefresh.showEmpty()
        } else {
            if (data.errorCode == 0) {
                binding.run {
                    if (layoutPageRefresh.state == RefreshState.Loading) {
                        recy.bindingAdapter.addModels(data.result?.data)
                    } else {
                        recy.bindingAdapter.setDifferModels(data.result?.data)
                    }
                    layoutPageRefresh.showContent()
                }
            } else {
                binding.layoutPageRefresh.showError("${data.errorCode}--${data.reason}")
            }
        }
    }
}
