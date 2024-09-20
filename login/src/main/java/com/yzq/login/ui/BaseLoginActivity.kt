package com.yzq.login.ui

import android.os.Bundle
import com.yzq.base.extend.immersiveRes
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.login.manager.PageManager


/**
 * @description: 全屏的activity基类
 * @author : yuzhiqiang
 */

open class BaseLoginActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersiveRes(com.yzq.base.R.color.white, true)
        PageManager.pushPage(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        PageManager.popPage(this)
    }

}