package com.yzq.kotlincommon.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivitySplashBinding
import com.yzq.lib_base.extend.navFinish
import com.yzq.lib_base.ui.activity.BaseViewBindingActivity
import kotlinx.coroutines.*

/**
 * @description: 闪屏页
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/11/25
 * @time   : 6:25 下午
 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        MainScope().launch {

            delay(1000)
            navFinish(RoutePath.Main.LOGIN)
        }
    }

}