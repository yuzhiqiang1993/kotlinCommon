package com.yzq.login.ui

import android.os.Bundle
import com.yzq.baseui.BaseActivity
import com.yzq.login.manager.PageManager
import com.yzq.util.ext.immersiveRes


/**
 * @description: 全屏的activity基类
 * @author : yuzhiqiang
 */

open class BaseLoginActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersiveRes(com.yzq.resource.R.color.white, true)
        PageManager.pushPage(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        PageManager.popPage(this)
    }

}