package com.yzq.kotlincommon.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.lib_base.extend.navFinish
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @description: 闪屏页
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/11/25
 * @time   : 6:25 下午
 */

class SplashActivity : AppCompatActivity(), SplashScreen.KeepOnScreenCondition {

    private var notReady = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*启动屏*/
        val splashScreen = installSplashScreen()
        /*设置保持住当前splash*/
        splashScreen.setKeepOnScreenCondition(this)
        setContentView(R.layout.activity_splash)

        MainScope().launch {
            LogUtils.i("模拟广告耗时")
            delay(2000)
            notReady = false
            LogUtils.i("跳转")
            navFinish(RoutePath.Main.LOGIN)
        }


    }

    override fun shouldKeepOnScreen(): Boolean {
        return notReady
    }

}