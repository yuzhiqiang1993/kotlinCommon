package com.yzq.kotlincommon.ui.activity

import androidx.core.app.ActivityOptionsCompat
import coil.load
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.setup
import com.drake.brv.utils.staggered
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.coroutine.ext.lifeScope
import com.yzq.data.juhe.toutiao.TouTiao
import com.yzq.img.ImgPreviewActivity
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.api.ApiService
import com.yzq.kotlincommon.databinding.ActivityImageListBinding
import com.yzq.kotlincommon.databinding.ItemImgListBinding
import com.yzq.logger.Logger
import com.yzq.net.RetrofitFactory
import com.yzq.network_status.NetworkStatus
import com.yzq.router.RoutePath
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

    private val binding by viewBinding(ActivityImageListBinding::inflate)

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
                    itemImgListBinding.ivImg.load(model.thumbnailPicS) {
                        crossfade(true)
//                        transformations(CircleCropTransformation())//图片圆角
                    }

                }

                R.id.iv_img.onClick {
                    val model = getModel<TouTiao.Result.Data>()
                    val itemMovieLayoutBinding = getBinding<ItemImgListBinding>()
                    val options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this@ImageListActivity,
                            itemMovieLayoutBinding.ivImg,
                            getString(R.string.img_transition)
                        )

                    ImgPreviewActivity.start(
                        this@ImageListActivity,
                        arrayListOf(model.thumbnailPicS),
                        0,
                        options
                    )
                }
            }
    }


    private fun requestData() {
//        lifecycleScope.launchSafety {
//            delay(10)//这里延迟10ms主要是为了让下面的catch先执行，否则会出现捕获不到异常的问题
//            if (!NetworkUtil.isConnected()) {
//                throw Throwable("网络未连接，请检查")
//            }
//            val listToutiao = RetrofitFactory.instance.getService(ApiService::class.java)
//                .listToutiao(page = page, pageSize = pageSize)
//            setData(listToutiao)
//        }.catch {
//            Logger.i("catch error:${it}")
//            binding.layoutPageRefresh.showError(it)
//        }.invokeOnCompletion {
//
//        }

        lifeScope {
            if (!NetworkStatus.isConnected()) {
                throw Throwable("网络未连接，请检查")
            }
            val listToutiao = RetrofitFactory.instance.getService(ApiService::class.java)
                .listToutiao(page = page, pageSize = pageSize)
            setData(listToutiao)
        }.catch {
            Logger.i("catch error:${it}")
            binding.layoutPageRefresh.showError(it)
        }.finally {
            Logger.i("finally")
        }
    }

    private fun setData(data: TouTiao?) {

        Logger.i("刷新状态:${binding.layoutPageRefresh.state}")
        if (data == null) {
            binding.layoutPageRefresh.showEmpty()
        } else {
            if (data.errorCode == 0) {
                binding.run {
                    if (layoutPageRefresh.state == RefreshState.Loading) {
                        /*加载更多*/
                        recy.bindingAdapter.addModels(data.result?.data)
                        /*无更多数据*/
//                        layoutPageRefresh.finishLoadMoreWithNoMoreData()
                    } else {
                        /*覆盖旧数据*/
                        recy.bindingAdapter.setDifferModels(data.result?.data)
                    }

                    layoutPageRefresh.showContent()
                }
            } else {
                Logger.i("${data.errorCode}==${data.reason}")
                binding.layoutPageRefresh.showError("${data.errorCode}--${data.reason}")

            }
        }
    }
}
