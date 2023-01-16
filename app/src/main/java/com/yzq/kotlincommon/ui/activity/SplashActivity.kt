package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import com.blankj.utilcode.util.LogUtils
import com.yzq.base.extend.navFinish
import com.yzq.common.constants.RoutePath
import com.yzq.common.utils.MMKVUtil
import com.yzq.materialdialog.showCallbackDialog
import com.yzq.statusbar.immersive
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
        LogUtils.i("onCreate")
        immersive(Color.WHITE, true)
        /*启动屏*/
        val splashScreen = installSplashScreen()
        /*设置保持住当前splash*/
        splashScreen.setKeepOnScreenCondition(this)
//        setContentView(R.layout.activity_splash)

        /*结束动画 在这里面才能加一些自己要显示的ui*/
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            LogUtils.i("setOnExitAnimationListener")
            immersive(Color.WHITE, true)//由于splash的主题执行完毕了，所以会显示App主题色的状态栏（默认主主色调是蓝色）不沉浸式的话看起来很怪
            handleRoute(splashScreenViewProvider)
        }

        MainScope().launch {
            LogUtils.i("模拟广告耗时")
            delay(200)
            notReady.compareAndSet(true, false)
        }

    }

    private fun handleRoute(splashScreenViewProvider: SplashScreenViewProvider) {
        if (MMKVUtil.appFirstOpen) {
            /*首次打开可以弹窗提示同意 隐私政策 */
            showCallbackDialog("提示", "同意隐私政策，用户协议",
                positiveCallback = {
                    MMKVUtil.appFirstOpen = false
                    /*在这里进行页面跳转*/
                    route(splashScreenViewProvider)

                },
                negativeCallback = {
//                    splashScreenViewProvider.remove()
                    finishAfterTransition()
                }
            )
        } else {
            route(splashScreenViewProvider)
        }

    }

    private fun route(splashScreenViewProvider: SplashScreenViewProvider) {
        if (MMKVUtil.hasLogin) {
            navFinish(RoutePath.Main.MAIN)
        } else {
            navFinish(RoutePath.Main.LOGIN)
        }

        /*下面的代码是可以执行一些动画的示例  可以但没必要...*/

//        /*整个页面*/
//        val splashScreenView = splashScreenViewProvider.view
//        /*中间显示的图片*/
//        val iconView = splashScreenViewProvider.iconView
//
//        /**
//         * iconView 动画
//         */
//        val iconViewAni = ObjectAnimator.ofFloat(
//            iconView,
//            View.ALPHA,
//            1f,
//            0.5f
//        )
//        iconViewAni.duration = 200
//        iconViewAni.interpolator = FastOutLinearInInterpolator()
//        iconViewAni.doOnEnd {
//            LogUtils.i("doOnEnd")
//
//if (MMKVUtil.hasLogin) {
//            navFinish(RoutePath.Main.MAIN)
//        } else {
//            navFinish(RoutePath.Main.LOGIN)
//        }
//
//
//        }
//        iconViewAni.start()//注意要cancle，否则可能会内存泄漏


    }

    override fun shouldKeepOnScreen(): Boolean {
        return notReady.get()
    }
}
