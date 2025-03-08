package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.hjq.permissions.Permission
import com.yzq.dialog.PromptDialog
import com.yzq.logger.Logger
import com.yzq.permission.getPermissions
import com.yzq.router.RoutePath
import com.yzq.router.navFinish
import com.yzq.storage.mmkv.MMKVDefault
import com.yzq.storage.mmkv.MMKVUser
import com.yzq.util.ext.immersive
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


    /**
     *  通过命令可以拉起：
     *  adb shell am start -n com.yzq.kotlincommon/.ui.activity.SplashActivity -e key1 value1 -e key2 value2
     *
     */

    companion object {
        const val TAG = "SplashActivity"
    }

    /*是否blockui*/
    private val keepOnScreenCondition = AtomicBoolean(true)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        Logger.it(TAG, "onCreate:${intent}")
        intent.extras?.let {
            for (key in it.keySet()) {
                Logger.it(TAG, "key:${key} value:${it[key]}")
            }
        }

        immersive(Color.WHITE, true)

        /*启动屏*/
        splashScreen.apply {
            /*设置保持住当前splash 主要用来设置动画执行间隔时间来确保动画能够完整的执行一次*/
            setKeepOnScreenCondition {
                return@setKeepOnScreenCondition keepOnScreenCondition.get()
            }
            /**
             * 自己处理动画 在这里面才能加一些自己要显示的ui
             */
            setOnExitAnimationListener {
                //动画结束完毕，这里不要去处理后需流程相关的业务逻辑，值处理动画，因为在Android 12上可能会出现不回调的情况
                Logger.it(TAG, "setOnExitAnimationListener callback")
            }

        }




        MainScope().launch {
            Logger.it(TAG, "MainScope start")
            //模拟闪屏页面的动画所需时间
            delay(300)
            Logger.it(TAG, "可以做一些初始化的逻辑，初始化完成后继续走")
            keepOnScreenCondition.set(false)//无需keepOnScreenCondition了

            /*
            * 已知在Android 12 上会有setOnExitAnimationListener可能不会被调用的问题
            * 但是低版本一些弹窗等ui必须要在 setOnExitAnimationListener 执行后再执行，不然可能会报错
            * 所以，这里最好延时一下给setOnExitAnimationListener执行的机会，但是在Android12的设备上首次启动会有白屏
            * */

            delay(200)

            handleRoute()
            Logger.it(TAG, "MainScope end")
        }

        Logger.it(TAG, "onCreate end")


    }


    @Synchronized
    private fun handleRoute() {


        Logger.it(TAG, "handleRoute开始执行")
//        //由于splash的主题执行完毕了，所以会显示App主题色的状态栏（默认主主色调是蓝色）不沉浸式的话看起来很怪
//        immersive(
//            Color.WHITE,
//            true
//        )


        /*先申请权限*/
        getPermissions(
            Permission.ACCESS_COARSE_LOCATION,
            Permission.ACCESS_FINE_LOCATION,
            Permission.ACCESS_BACKGROUND_LOCATION,
            permissionDenide = { deniedPermissions, doNotAskAgain ->
                Logger.it(TAG, "权限被拒绝了:${deniedPermissions}")
                finish()
            }) {
            Logger.it(TAG, "有权限,进页面")
            if (MMKVDefault.appFirstOpen) {
                Logger.it(TAG, "首次打开:${MMKVDefault.appFirstOpen}")/*首次打开可以弹窗提示同意 隐私政策 */

                PromptDialog(this).apply {
                    content("同意隐私政策，用户协议")
                }.positiveBtn("同意") { v ->
                    MMKVDefault.appFirstOpen = false/*在这里进行页面跳转*/
                    route()
                }.negativeBtn("退出") { v ->
                    finishAfterTransition()
                }.safeShow()

            } else {
                route()
            }
        }


    }

    private fun route() {
        Logger.it(TAG, "route 跳转页面")
        if (MMKVUser.hasLogin) {
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
//            Logger.it(TAG,TAG,"doOnEnd")
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


    override fun onDestroy() {
        super.onDestroy()
        Logger.it(TAG, "onDestroy")
    }

}
