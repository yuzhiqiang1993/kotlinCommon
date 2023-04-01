package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.blankj.utilcode.util.LogUtils
import com.hjq.permissions.Permission
import com.yzq.base.extend.navFinish
import com.yzq.common.constants.RoutePath
import com.yzq.common.utils.MMKVUtil
import com.yzq.materialdialog.showCallbackDialog
import com.yzq.permission.getPermissions
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
class SplashActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SplashActivity"
        private val handleRouteInvoke = AtomicBoolean(false)
    }

    private val keepOnScreenCondition = AtomicBoolean(true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        immersive(Color.WHITE, true)

        /*启动屏*/
        installSplashScreen().apply {
            /*设置保持住当前splash 主要用来设置动画执行间隔时间来确保动画能够完整的执行一次*/
            setKeepOnScreenCondition {
                return@setKeepOnScreenCondition keepOnScreenCondition.get()
            }
            /**
             * 自己处理动画 在这里面才能加一些自己要显示的ui
             */
            setOnExitAnimationListener {
                /**
                 * Android12的设备上 debug时或者被其他应用拉起的打开方式会出现setOnExitAnimationListener回调不执行的情况
                 *
                 */
                LogUtils.i("setOnExitAnimationListener")
                immersive(
                    Color.WHITE,
                    true
                )//由于splash的主题执行完毕了，所以会显示App主题色的状态栏（默认主主色调是蓝色）不沉浸式的话看起来很怪

                /*路由*/
//                handleRoute()
            }

        }

        MainScope().launch {
            LogUtils.i("可以做一些初始化的逻辑，初始化完成后继续走")

            /*解除blockui，正常情况下setOnExitAnimationListener会调用  已知Android12不会被调用*/
            keepOnScreenCondition.compareAndSet(true, false)
            LogUtils.i("可以执行 setOnExitAnimationListener 了")

            /*
            * 已知在Android 12 上会有setOnExitAnimationListener可能不会被调用的问题
            * 但是低版本一些弹窗等ui必须要在 setOnExitAnimationListener 执行后再执行，不然可能会报错
            * 所以，这里最好延时一下给setOnExitAnimationListener执行的机会
            * */

            delay(200)

            val handleRouteInovke = handleRouteInvoke.get()
            LogUtils.i("handleRouteInovke:${handleRouteInovke}")
            if (!handleRouteInovke) {
                LogUtils.i("handleRouteInvoke 未执行===")
                handleRoute()
            }
        }


    }


    @Synchronized
    private fun handleRoute() {
        if (handleRouteInvoke.get()) {
            LogUtils.i("handleRoute 已经执行过了.....")
            return
        }
        handleRouteInvoke.compareAndSet(false, true)
        /*先申请权限*/
        getPermissions(
            Permission.ACCESS_COARSE_LOCATION,
            Permission.ACCESS_FINE_LOCATION,
            Permission.ACCESS_BACKGROUND_LOCATION,
            permissionDenide = { deniedPermissions, doNotAskAgain ->
                LogUtils.i("权限被拒绝了:${deniedPermissions}")
                finish()
            }
        ) {

            if (MMKVUtil.appFirstOpen) {
                LogUtils.i("首次打开:${MMKVUtil.appFirstOpen}")
                /*首次打开可以弹窗提示同意 隐私政策 */
                showCallbackDialog("提示", "同意隐私政策，用户协议", positiveCallback = {
                    MMKVUtil.appFirstOpen = false
                    /*在这里进行页面跳转*/
                    route()
                }, negativeCallback = {
                    finishAfterTransition()
                })
            } else {
                route()
            }
        }


    }

    private fun route() {

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

}
