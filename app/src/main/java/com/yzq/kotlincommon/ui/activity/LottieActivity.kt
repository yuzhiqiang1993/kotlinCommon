package com.yzq.kotlincommon.ui.activity

import android.animation.Animator
import android.graphics.Color
import com.blankj.utilcode.util.LogUtils
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityLottieBinding


/**
 * @description LottieView
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/4/26
 * @time    15:14
 */
@Route(path = RoutePath.Main.LOTTIE)
class LottieActivity : BaseActivity() {
    private val binding by viewbind(ActivityLottieBinding::inflate)

    override fun initWidget() {
        super.initWidget()
        initToolbar(binding.includedToolbar.toolbar, "Lottie")

        binding.run {
            lottieView.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    LogUtils.i("onAnimationStart")
                }

                override fun onAnimationEnd(animation: Animator) {
                    LogUtils.i("onAnimationEnd")
                }

                override fun onAnimationCancel(animation: Animator) {
                    LogUtils.i("onAnimationCancel")
                }

                override fun onAnimationRepeat(animation: Animator) {
                    LogUtils.i("onAnimationRepeat")
                }

            })

            lottieView.addAnimatorUpdateListener {
                LogUtils.i("animatedFraction:${it.animatedFraction}")
            }
            val url = "https://assets7.lottiefiles.com/packages/lf20_5lTxAupekw.json"
            lottieView.setBackgroundColor(Color.parseColor("#3490dc"))
            lottieView.setAnimationFromUrl(url)

        }
    }
}