package com.yzq.login.ui

import android.os.Bundle
import com.yzq.base.extend.immersiveRes
import com.yzq.base.ui.activity.BaseActivity


/**
 * @description: 全屏的activity基类
 * @author : yuzhiqiang
 */

open class BasePopupActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersiveRes(com.yzq.widget.R.color.popup_color, true)
    }


}