package com.yzq.kotlincommon.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.lib_base.extend.navFinish

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
        navFinish(RoutePath.Main.LOGIN)

    }

}