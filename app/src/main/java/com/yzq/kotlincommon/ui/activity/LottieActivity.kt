package com.yzq.kotlincommon.ui.activity

import android.animation.Animator
import android.graphics.Color
import com.airbnb.lottie.LottieCompositionFactory
import com.therouter.router.Route
import com.yzq.application.AppContext
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityLottieBinding
import com.yzq.logger.Logger


/**
 * @description LottieView
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/4/26
 * @time    15:14
 */
@Route(path = RoutePath.Main.LOTTIE)
class LottieActivity : BaseActivity() {
    private val binding by viewBinding(ActivityLottieBinding::inflate)

    override fun initWidget() {
        super.initWidget()
        initToolbar(binding.includedToolbar.toolbar, "Lottie")

        binding.run {
            lottieView.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    Logger.i("onAnimationStart")
                }

                override fun onAnimationEnd(animation: Animator) {
                    Logger.i("onAnimationEnd")
                }

                override fun onAnimationCancel(animation: Animator) {
                    Logger.i("onAnimationCancel")
                }

                override fun onAnimationRepeat(animation: Animator) {
//                    Logger.i("onAnimationRepeat")
                }

            })

            lottieView.addAnimatorUpdateListener {
//                Logger.i("animatedFraction:${it.animatedFraction}")
            }

//            val url = "https://assets7.lottiefiles.com/packages/lf20_5lTxAupekw.json"
            val url =
                "https://sares.kfc.com.cn/uatfile/ad/20240501/57130846fb204916aa8c6b34c93eac8e.json"
            lottieView.setBackgroundColor(Color.parseColor("#3490dc"))


            /**
             * 返回 LottieComposition 对象：LottieCompositionFactory.fromUrl() 方法返回一个 LottieComposition 对象，而不是直接设置动画到 LottieAnimationView 中。这使得你可以在加载完成后对动画进行更多的操作，例如缓存、复用、延迟加载等。
             * 后台线程加载：由于该方法通常在后台线程中执行，因此它不会阻塞主线程，可以确保应用的流畅性和响应性。你可以在加载过程中显示加载进度或者其他用户界面反馈。
             * 自定义加载策略：你可以根据需要自定义加载过程的细节和策略，例如设置超时时间、重试机制、缓存策略等。这样可以更好地适应不同的网络环境和应用需求。
             * 对加载过程的控制：你可以监控加载过程中的状态，并对加载过程进行相应的处理，例如处理加载失败、加载进度等情况。
             * 适用于高级用途：LottieCompositionFactory.fromUrl() 方法适用于更复杂的场景，例如需要加载多个动画并进行组合、需要从远程服务器动态加载动画等情况。
             * 综上所述，LottieCompositionFactory.fromUrl() 方法的优势在于它提供了更多的灵活性和控制力，适用于对加载过程有特殊需求或者需要进行更复杂处理的情况。
             */
            LottieCompositionFactory.fromUrl(AppContext, url).addListener {
                val hasImages = it.hasImages()
                Logger.i("hasImages:$hasImages")
                if (hasImages) {
                    Logger.i("images:${it.images}")
                    it.images.iterator().forEach {
                        Logger.i("image item->${it.key}:${it.value.fileName}")
                    }

                    //如果Lottie动画中的图片资源放在了assets/images文件夹下，可以通过设置imageAssetsFolder来指定
                    lottieView.imageAssetsFolder = "images/"
                }
                lottieView.setComposition(it)
                lottieView.playAnimation()
            }.addFailureListener {
                Logger.e("LottieCompositionFactory.fromUrl onFailure", it)
            }

            //setAnimationFromUrl适用于简单的动画场景
//            lottieView.setAnimationFromUrl(url)

        }
    }
}