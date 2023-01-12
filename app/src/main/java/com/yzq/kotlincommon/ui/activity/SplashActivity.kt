package com.yzq.kotlincommon.ui.activity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.blankj.utilcode.util.LogUtils
import com.yzq.base.extend.navFinish
import com.yzq.common.constants.RoutePath
import com.yzq.statusbar.setFullscreen
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @description: 闪屏页
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2021/11/25
 * @time : 6:25 下午
 */

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), SplashScreen.KeepOnScreenCondition {

    private var notReady = AtomicBoolean(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullscreen()

        /*启动屏*/
        val splashScreen = installSplashScreen()
        /*设置保持住当前splash*/
        splashScreen.setKeepOnScreenCondition(this)
//        setContentView(R.layout.activity_splash)

        /*结束动画  不加这个在低版本会有卡一下的感觉  页面切换不连贯*/
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            val splashScreenView = splashScreenViewProvider.view
            val iconView = splashScreenViewProvider.iconView
            val objectAnimator = ObjectAnimator.ofFloat(
                iconView,
                View.ALPHA,
                iconView.alpha,
                splashScreenView.alpha.toFloat()
            )
            objectAnimator.interpolator = AnticipateInterpolator()
            objectAnimator.duration = 2000L
            objectAnimator.doOnEnd { splashScreenViewProvider.remove() }
            objectAnimator.start()

        }

        MainScope().launch {
            LogUtils.i("模拟广告耗时")
            delay(500)
            notReady.compareAndSet(true, false)
            navFinish(RoutePath.Main.LOGIN)
        }
    }

    override fun shouldKeepOnScreen(): Boolean {
        return notReady.get()
    }
}
