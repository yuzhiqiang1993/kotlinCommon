package com.yzq.login.ui

import android.os.Build
import android.os.Bundle
import com.yzq.base.extend.immersiveRes
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.login.R


/**
 * @description: 全屏的activity基类
 * @author : yuzhiqiang
 */

open class BasePopupActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersiveRes(com.yzq.widget.R.color.popup_color, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.slide_in_bottom, 0, 0)
        } else {
            overridePendingTransition(R.anim.slide_in_bottom, 0)
        }
    }


    override fun finish() {
        super.finish()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, 0, R.anim.slide_out_top, 0)
        } else {
            overridePendingTransition(0, R.anim.slide_out_top)
        }

    }
}