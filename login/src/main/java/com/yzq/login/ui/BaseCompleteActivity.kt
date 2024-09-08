package com.yzq.login.ui

import android.os.Bundle
import com.yzq.base.extend.immersiveRes
import com.yzq.base.ui.activity.BaseActivity


/**
 * @description: 全屏的activity基类
 * @author : yuzhiqiang
 */

open class BaseCompleteActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersiveRes(com.yzq.base.R.color.white, false)
    }


}